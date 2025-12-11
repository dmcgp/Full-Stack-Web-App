package com.example.taskmanager.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class JwtService {
    private final Key key;
    private final long accessTtlMillis;
    private final long refreshTtlMillis;

    public JwtService(
            @Value("${app.jwt.secret:changeme-secret}") String secret,
            @Value("${app.jwt.accessTtlSeconds:3600}") long accessTtlSeconds,
            @Value("${app.jwt.refreshTtlSeconds:604800}") long refreshTtlSeconds
    ) {
        this.key = deriveKey(secret);
        this.accessTtlMillis = accessTtlSeconds * 1000L;
        this.refreshTtlMillis = refreshTtlSeconds * 1000L;
    }

    private Key deriveKey(String secret) {
        try {
            byte[] hashed = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(hashed);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to derive JWT key", e);
        }
    }

    public String generateAccessToken(User user) {
        return buildToken(user, accessTtlMillis, Map.of("role", user.getRole().name()));
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, refreshTtlMillis, Map.of("type", "refresh"));
    }

    private String buildToken(User user, long ttlMillis, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(extraClaims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(ttlMillis)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateAndParse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
