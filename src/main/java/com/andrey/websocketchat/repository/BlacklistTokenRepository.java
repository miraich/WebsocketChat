package com.andrey.websocketchat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Profile("!test")
public class BlacklistTokenRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public static final String REFRESH_BLACKLIST_PREFIX = "blacklist:refresh:";
    public static final String REFRESH_ACCESS_PREFIX = "blacklist:access:";

    /**
     * Blacklist a refresh token
     */
    public void blacklistRefreshToken(String refreshToken, Instant expiresAt) {
        long ttlMillis = expiresAt.toEpochMilli() - Instant.now().toEpochMilli();
        if (ttlMillis > 0) {
            redisTemplate.opsForValue().set(
                    REFRESH_BLACKLIST_PREFIX + refreshToken,
                    "blacklisted",
                    ttlMillis,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    /**
     * Blacklist a access token
     */
    public void blacklistAccessToken(String accessToken, Instant expiresAt) {
        long ttlMillis = expiresAt.toEpochMilli() - Instant.now().toEpochMilli();
        if (ttlMillis > 0) {
            redisTemplate.opsForValue().set(
                    REFRESH_ACCESS_PREFIX + accessToken,
                    "blacklisted",
                    ttlMillis,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    /**
     * Check if a access token is blacklisted
     */
    public boolean isAccessTokenBlacklisted(String token) {
        String key = REFRESH_ACCESS_PREFIX + token;
        return Objects.requireNonNull(redisTemplate.hasKey(key));
    }

    /**
     * Check if a refresh token is blacklisted
     */
    public boolean isRefreshTokenBlacklisted(String token) {
        String key = REFRESH_BLACKLIST_PREFIX + token;
        return Objects.requireNonNull(redisTemplate.hasKey(key));
    }
}
