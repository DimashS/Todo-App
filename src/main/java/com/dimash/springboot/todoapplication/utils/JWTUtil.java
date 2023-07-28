package com.dimash.springboot.todoapplication.utils;

import com.dimash.springboot.todoapplication.security.PersonDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    @Value("${jwt_access_secret}")
    private String accessKey;
    @Value("${jwt_refresh_secret}")
    private String refreshKey;

    public String generateAccessToken(@NonNull PersonDetails personDetails) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(personDetails.getUsername())
                .setExpiration(accessExpiration)
                .signWith(getSigningKey(accessKey))
                .setIssuedAt(new Date())
                .claim("role", personDetails.getAuthorities())
                .compact();
    }

    public String generateRefreshToken(@NonNull PersonDetails personDetails) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(personDetails.getUsername())
                .setExpiration(refreshExpiration)
                .setIssuedAt(new Date())
                .claim("role", personDetails.getAuthorities())
                .signWith(getSigningKey(refreshKey))
                .compact();
    }

    private Key getSigningKey(String accessKey) {
        byte[] getBytes = accessKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(getBytes);
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secret))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Some troubles with jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, accessKey);
    }

    public Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey(secret)).build().parseClaimsJws(token).getBody();
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, refreshKey);
    }

}
