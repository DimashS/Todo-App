package com.dimash.springboot.todoapplication.utils;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class jwtTokenUtils {
    private String secret;

    private Duration jwtLifeTimeExpiration;

}
