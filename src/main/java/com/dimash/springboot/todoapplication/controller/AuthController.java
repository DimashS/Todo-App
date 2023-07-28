package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.AuthenticationDTO;
import com.dimash.springboot.todoapplication.dto.PersonDTO;
import com.dimash.springboot.todoapplication.request.RefreshJWTRequest;
import com.dimash.springboot.todoapplication.response.AuthResponse;
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthenticationDTO authenticationDTO) {
        AuthResponse auth = authService.authUser(authenticationDTO.getName(), authenticationDTO.getPassword());
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getNewAccessToken(@RequestBody RefreshJWTRequest refreshJWTRequest) {
        AuthResponse tokenResponse = authService.getAccessToken(refreshJWTRequest.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> getNewRefreshToken(@RequestBody RefreshJWTRequest refreshJWTRequest) {
        AuthResponse tokenResponse = authService.getRefreshToken(refreshJWTRequest.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
