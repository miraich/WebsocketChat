package com.andrey.websocketchat.service.impl;

import com.andrey.websocketchat.enums.TokenType;
import com.andrey.websocketchat.exception.EntityAlreadyExistsException;
import com.andrey.websocketchat.model.AccessToken;
import com.andrey.websocketchat.model.AuthenticationResult;
import com.andrey.websocketchat.model.User;
import com.andrey.websocketchat.model.UserPrincipal;
import com.andrey.websocketchat.service.AuthManagementService;
import com.andrey.websocketchat.service.entity.JwtService;
import com.andrey.websocketchat.service.entity.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class AuthManagementServiceImpl implements AuthManagementService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthenticationResult registerUser(User user, HttpServletResponse response) {
        if (userService.existsByName(user.getUsername())) {
            throw new EntityAlreadyExistsException(String.format("User with name %s already exists", user.getUsername()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userService.save(user);
        UserPrincipal userPrincipal = new UserPrincipal(savedUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userPrincipal,
                null,
                userPrincipal.getAuthorities()
        );
        return generateTokensCreateRs(response, auth);
    }

    public AuthenticationResult login(User user, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    user.getPassword()
                            )
                    );
            return generateTokensCreateRs(response, auth);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public void logout(String refreshToken, Jwt accessToken) {
        Jwt jwt = jwtService.decode(refreshToken);
        jwtService.blacklistRefreshToken(refreshToken, jwt.getExpiresAt());
        jwtService.blacklistAccessToken(accessToken.getTokenValue(), jwt.getExpiresAt());
    }

    public AccessToken refresh(String refreshToken, HttpServletResponse response) {
        Jwt jwt = jwtService.decode(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getClaimAsString("username"));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        String accessToken = jwtService.generateToken(auth, TokenType.accessToken, 15, ChronoUnit.MINUTES);
        String refreshTokenGen = jwtService.generateToken(auth, TokenType.refreshToken, 1, ChronoUnit.DAYS);

        jwtService.blacklistRefreshToken(refreshToken, jwt.getExpiresAt());
        ResponseCookie refreshCookie = generateRefreshCookie(refreshTokenGen);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        return new AccessToken(accessToken);
    }

    private AuthenticationResult generateTokensCreateRs(HttpServletResponse response, Authentication auth) {
        String accessToken = jwtService.generateToken(auth, TokenType.accessToken, 15, ChronoUnit.MINUTES);
        String refreshToken = jwtService.generateToken(auth, TokenType.refreshToken, 1, ChronoUnit.DAYS);

        ResponseCookie refreshCookie = generateRefreshCookie(refreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        return new AuthenticationResult(user, accessToken);
    }

    private ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(Duration.of(1, ChronoUnit.DAYS))
                .sameSite("Strict")
                .build();
    }
}
