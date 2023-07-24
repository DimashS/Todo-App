package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.response.LoginResponse;
import com.dimash.springboot.todoapplication.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PersonRepository personRepository) {
        this.authenticationManager = authenticationManager;
        this.personRepository = personRepository;
    }

    @Override
    public LoginResponse authUser(String name, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(name, password));
            return new LoginResponse(personRepository.findByUsername(name).get());
        } catch (AuthenticationException e) {
            return new LoginResponse(null);
        }
    }
}
