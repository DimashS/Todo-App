package com.dimash.springboot.todoapplication.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;

    public JwtProvider(@Value("${jwt.access.secret}") SecretKey accessSecret,@Value("${jwt.refresh.secret")SecretKey refreshSecret) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
    }
}
