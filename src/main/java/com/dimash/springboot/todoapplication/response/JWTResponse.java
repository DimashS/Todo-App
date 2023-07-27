package com.dimash.springboot.todoapplication.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JWTResponse {
    private final String accessToken;
    private final String refreshToken;
}
