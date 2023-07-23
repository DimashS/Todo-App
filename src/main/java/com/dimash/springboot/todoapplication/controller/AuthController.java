package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.AuthenticationDTO;
import com.dimash.springboot.todoapplication.dto.PersonDTO;
import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.service.serviceImpl.RegistrationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationServiceImpl registrationService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthController(RegistrationServiceImpl registrationService, AuthenticationManager authenticationManager,
                          ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody @Valid PersonDTO personDTO) {
        try {
            Person person = convertToPerson(personDTO);
            registrationService.registerUser(person);
            return ResponseEntity.ok("Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                            authenticationDTO.getUsername(), null));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Successful authentication");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something wrong!");
        }
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
