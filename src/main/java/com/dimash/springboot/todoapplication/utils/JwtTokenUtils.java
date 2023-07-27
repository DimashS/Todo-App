package com.dimash.springboot.todoapplication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dimash.springboot.todoapplication.security.PersonDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtils {
    @Value("${jwt_access_secret}")
    private static String accessSecretBASE64;

    @Value("${jwt_refresh_secret}")
    private static String refreshSecretBASE64;

    @Value("${jwt_access_expiration_time}")
    private static Long accessTokenExpirationTimeMillis;

    @Value("${jwt_refresh_expiration_time}")
    private static Long refreshTokeExpirationTimeMillis;


    public static String generateAccessToken(PersonDetails personDetails) {
        return generateToken(personDetails, accessTokenExpirationTimeMillis, accessSecretBASE64);
    }

    public static String generateRefreshToken(PersonDetails personDetails) {
        return generateToken(personDetails, refreshTokeExpirationTimeMillis, refreshSecretBASE64);
    }

    public static String generateToken(PersonDetails personDetails, Long expirationTime, String key) {
        Date expTime = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder().claim("roles", personDetails.getAuthorities())
                .setSubject(personDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expTime)
                .signWith(getSigningKey(key), SignatureAlgorithm.ES256).compact();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String validateAndRetrieveClaim(String token, String secret)
            throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }
}
