package com.andrey.websocketchat.service;

import com.andrey.websocketchat.model.AccessToken;
import com.andrey.websocketchat.model.AuthenticationResult;
import com.andrey.websocketchat.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthManagementService {
    AuthenticationResult registerUser(User user, HttpServletResponse response);

    AuthenticationResult login(User user, HttpServletResponse response);

    void logout(String refreshToken, Jwt accessToken);

    AccessToken refresh(String refreshToken);
}
