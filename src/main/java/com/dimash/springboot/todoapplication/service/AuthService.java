package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.response.LoginResponse;

public interface AuthService {
    public LoginResponse authUser(String name, String password);
}
