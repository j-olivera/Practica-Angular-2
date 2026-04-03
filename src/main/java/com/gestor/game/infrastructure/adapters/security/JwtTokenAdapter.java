package com.gestor.game.infrastructure.adapters.security;

import com.gestor.game.application.port.out.security.JwtTokenPort;
import com.gestor.game.infrastructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

public class JwtTokenAdapter implements JwtTokenPort {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtTokenAdapter(JwtProperties props) {
        this.props = props;
        if (props.secret() == null || props.secret().length() < 32) {
            throw new IllegalArgumentException("jwt.secret must be at least 32 characters");
        }
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createAccessToken(Long userId, String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + props.expirationMs());
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    @Override
    public Optional<Long> validateAndGetUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(Long.parseLong(claims.getSubject()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
