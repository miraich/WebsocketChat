package com.andrey.websocketchat.service.entity;

import com.andrey.websocketchat.enums.TokenType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public interface JwtService {
    boolean isRefreshTokenBlacklisted(String refreshToken);

    boolean isAccessTokenBlacklisted(String refreshToken);

    void blacklistRefreshToken(String refreshToken, Instant expiresAt);

    void blacklistAccessToken(String refreshToken, Instant expiresAt);

    boolean isRefreshToken(Jwt jwt);

    Jwt decode(String token);

    String generateToken(Authentication auth, TokenType tokenType, long expiresInAmount, ChronoUnit chronoUnit);
}
