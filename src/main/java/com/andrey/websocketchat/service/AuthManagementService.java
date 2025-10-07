package com.andrey.websocketchat.service;

import com.andrey.websocketchat.entity.AuthenticationResult;
import com.andrey.websocketchat.entity.User;
import com.andrey.websocketchat.exception.UserAlreadyExistsException;
import com.andrey.websocketchat.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthManagementService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthenticationResult registerUser(User user) {
        if (userService.existsByName(user.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userService.save(user);
        String token = jwtService.generateToken(savedUser);
        return new AuthenticationResult(savedUser, token);
    }
}
