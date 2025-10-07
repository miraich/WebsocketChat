package com.andrey.websocketchat.controller;

import com.andrey.websocketchat.dto.auth.SignUpRq;
import com.andrey.websocketchat.dto.auth.SignUpRs;
import com.andrey.websocketchat.entity.AuthenticationResult;
import com.andrey.websocketchat.mapper.AuthMapper;
import com.andrey.websocketchat.mapper.UserMapper;
import com.andrey.websocketchat.service.AuthManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final AuthManagementService authManagementService;

    @PostMapping("/register")
    public SignUpRs register(@RequestBody SignUpRq user) {
        AuthenticationResult result = authManagementService.registerUser(userMapper.map(user));
        return authMapper.map(result.user(), result.token());
    }
}
