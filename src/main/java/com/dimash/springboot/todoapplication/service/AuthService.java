package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.dto.AuthenticationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<?> authUser(AuthenticationDTO authenticationDTO);
}
