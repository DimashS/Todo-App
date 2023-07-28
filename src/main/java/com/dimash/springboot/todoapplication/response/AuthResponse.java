package com.dimash.springboot.todoapplication.response;

import lombok.Data;

@Data
public class AuthResponse {
    private final String successString;
    private final String accessToken;
    private final String refreshToken;
}
