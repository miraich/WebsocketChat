package com.andrey.websocketchat.service;

import com.andrey.websocketchat.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("websocket-chat-app")
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(user.getId().toString())
                .claim("scope", user.getRole())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
