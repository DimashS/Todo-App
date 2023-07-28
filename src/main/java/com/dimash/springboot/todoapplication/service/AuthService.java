package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.response.AuthResponse;

public interface AuthService {
    public AuthResponse authUser(String name, String password);
}
