package com.andrey.websocketchat.controller;

import com.andrey.websocketchat.dto.auth.SignInRq;
import com.andrey.websocketchat.dto.auth.SignInRs;
import com.andrey.websocketchat.dto.auth.SignUpRq;
import com.andrey.websocketchat.dto.auth.SignUpRs;
import com.andrey.websocketchat.entity.AuthenticationResult;
import com.andrey.websocketchat.mapper.AuthMapper;
import com.andrey.websocketchat.mapper.UserMapper;
import com.andrey.websocketchat.service.AuthManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
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
    public SignUpRs register(@RequestBody @Valid SignUpRq signUpRq) {
        AuthenticationResult result = authManagementService.registerUser(userMapper.map(signUpRq));
        return authMapper.mapToSingUpRs(result.user(), result.token());
    }

    @PostMapping("/login")
    public SignInRs login(@RequestBody @Valid SignInRq signInRq) {
        AuthenticationResult result = authManagementService.login(userMapper.map(signInRq));
        return authMapper.mapToSingInRs(result.user(), result.token());
    }
}
