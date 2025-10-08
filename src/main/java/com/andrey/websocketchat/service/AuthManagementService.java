package com.andrey.websocketchat.service;

import com.andrey.websocketchat.entity.AuthenticationResult;
import com.andrey.websocketchat.entity.User;
import com.andrey.websocketchat.exception.EntityAlreadyExistsException;
import com.andrey.websocketchat.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class AuthManagementService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResult registerUser(User user) {
        if (userService.existsByName(user.getUsername())) {
            throw new EntityAlreadyExistsException(String.format("User with name %s already exists", user.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userService.save(user);
        String token = jwtService.generateAccessToken(savedUser);
        return new AuthenticationResult(savedUser, token);
    }

    public AuthenticationResult login(User user) {
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        user = userService.getByUsername(user.getUsername());

        System.out.println(authToken.getName());
        String token = jwtService.generateAccessToken(user);
        return new AuthenticationResult(user, token);
    }
}
