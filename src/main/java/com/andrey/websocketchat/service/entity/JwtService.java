package com.andrey.websocketchat.service.entity;

import java.time.Instant;

public interface TokenService {
    boolean isRefreshTokenBlacklisted(String refreshToken);

    void blacklistRefreshToken(String refreshToken, Instant expiresAt);
}
