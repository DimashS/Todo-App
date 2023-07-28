package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.response.LoginResponse;
import com.dimash.springboot.todoapplication.security.PersonDetails;
import com.dimash.springboot.todoapplication.service.AuthService;
import com.dimash.springboot.todoapplication.service.PersonDetailsService;
import com.dimash.springboot.todoapplication.utils.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PersonDetailsService personDetailsService;
    private final JWTUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PersonDetailsService personDetailsService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.personDetailsService = personDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse authUser(String name, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(name, password));
            PersonDetails personDetails = personDetailsService.loadUserByUsername(name);
            boolean isPasswordMatched = new BCryptPasswordEncoder().matches(
                    password, personDetails.getPassword());
            String accessToken = null;
            String refreshToken = null;
            if (isPasswordMatched) {
                accessToken = jwtUtil.generateAccessToken(personDetails);
                refreshToken = jwtUtil.generateRefreshToken(personDetails);
            }
            return new LoginResponse("Authorization successfull!", accessToken,refreshToken);
        } catch (AuthenticationException e) {
            return new LoginResponse("Authorization failed!", null, null);
        }
    }
}
