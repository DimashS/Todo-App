package com.dimash.springboot.todoapplication.utils;

import com.dimash.springboot.todoapplication.security.PersonDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
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
        Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
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

    public boolean validateAccessToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            expEx.getCause();
        } catch (UnsupportedJwtException unsEx) {
        } catch (MalformedJwtException mjEx) {
        } catch (SignatureException sEx) {
        } catch (Exception e) {
        }
        return false;
    }


}
