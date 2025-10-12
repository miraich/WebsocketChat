package com.andrey.websocketchat.service.impl;

import com.andrey.websocketchat.enums.TokenType;
import com.andrey.websocketchat.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class JwtServiceImpl {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateToken(Authentication auth, TokenType tokenType, long expiresInAmount, ChronoUnit chronoUnit) {
        Instant now = Instant.now();
        UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("websocket-chat-app")
                .issuedAt(now)
                .expiresAt(now.plus(expiresInAmount, chronoUnit))
                .subject(userDetails.getId().toString())
                .claim("roles", roles)
                .claim("username", userDetails.getUsername())
                .claim("type", tokenType.name())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }

    public boolean isTokenExpired(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        Instant expiresAt = jwt.getExpiresAt();
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}
