package com.andrey.websocketchat.controller;

import com.andrey.websocketchat.dto.auth.AccessTokenRs;
import com.andrey.websocketchat.dto.auth.SignInRq;
import com.andrey.websocketchat.dto.auth.SignInRs;
import com.andrey.websocketchat.dto.auth.SignUpRq;
import com.andrey.websocketchat.dto.auth.SignUpRs;
import com.andrey.websocketchat.mapper.AuthMapper;
import com.andrey.websocketchat.mapper.UserMapper;
import com.andrey.websocketchat.model.AuthenticationResult;
import com.andrey.websocketchat.service.AuthManagementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Profile("!test")
@RequestMapping("/api/auth")
public class AuthController {
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final AuthManagementService authManagementService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpRs register(@RequestBody @Valid SignUpRq signUpRq, HttpServletResponse response) {
        AuthenticationResult result = authManagementService.registerUser(userMapper.map(signUpRq), response);
        return authMapper.mapToSignUpRs(result.userPrincipal(), result.accessToken());
    }

    @PostMapping("/login")
    public SignInRs login(@RequestBody @Valid SignInRq signInRq, HttpServletResponse response) {
        AuthenticationResult result = authManagementService.login(userMapper.map(signInRq), response);
        return authMapper.mapToSignInRs(result.userPrincipal(), result.accessToken());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@CookieValue("refreshToken") String refreshToken, @AuthenticationPrincipal Jwt accessToken) {
        authManagementService.logout(refreshToken, accessToken);
    }

    @PostMapping("/refresh")
    public AccessTokenRs refresh(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        return authMapper.map(authManagementService.refresh(refreshToken, response));
    }
}
