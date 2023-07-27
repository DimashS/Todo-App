package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.AuthenticationDTO;
import com.dimash.springboot.todoapplication.dto.PersonDTO;
import com.dimash.springboot.todoapplication.response.LoginResponse;
import com.dimash.springboot.todoapplication.service.serviceImpl.AuthServiceImpl;
import com.dimash.springboot.todoapplication.service.serviceImpl.RegistrationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationServiceImpl registrationService;
    private final AuthServiceImpl authService;

    public AuthController(RegistrationServiceImpl registrationService, AuthServiceImpl authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody PersonDTO personDTO) {
        try {
            registrationService.registerUser(personDTO);
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok().body(authService.authUser(authenticationDTO));
    }
}
