package com.andrey.websocketchat.service;

import com.andrey.websocketchat.exception.EntityAlreadyExistsException;
import com.andrey.websocketchat.model.AuthenticationResult;
import com.andrey.websocketchat.model.User;
import com.andrey.websocketchat.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class CustomAuthManagementService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final CustomJwtService customJwtService;

    public AuthenticationResult registerUser(User user) {
        if (userService.existsByName(user.getUsername())) {
            throw new EntityAlreadyExistsException(String.format("User with name %s already exists", user.getUsername()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userService.save(user);
        String token = customJwtService.generateAccessToken(savedUser);
        return new AuthenticationResult(savedUser, token);
    }

    public AuthenticationResult login(User user) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    user.getPassword()
                            )
                    );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.getByUsername(userDetails.getUsername());
            String token = customJwtService.generateAccessToken(user);
            return new AuthenticationResult(user, token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
