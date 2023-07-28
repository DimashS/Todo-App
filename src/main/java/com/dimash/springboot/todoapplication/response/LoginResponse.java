package com.dimash.springboot.todoapplication.response;

import com.dimash.springboot.todoapplication.model.Person;
import lombok.Data;

@Data
public class LoginResponse {
    private final String successString;
    private final String accessToken;
    private final String refreshToken;
}
