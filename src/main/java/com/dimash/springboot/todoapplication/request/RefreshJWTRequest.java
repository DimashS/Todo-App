package com.dimash.springboot.todoapplication.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJWTRequest {
    public String refreshToken;
}
