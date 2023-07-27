package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.dto.AuthenticationDTO;
import com.dimash.springboot.todoapplication.response.JWTResponse;
import com.dimash.springboot.todoapplication.response.LoginResponse;
import com.dimash.springboot.todoapplication.security.PersonDetails;
import com.dimash.springboot.todoapplication.service.AuthService;
import com.dimash.springboot.todoapplication.service.PersonDetailsService;
import com.dimash.springboot.todoapplication.utils.JwtTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PersonDetailsService personDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PersonDetailsService personDetailsService, PersonDetailsService personDetailsService1) {
        this.authenticationManager = authenticationManager;
        this.personDetailsService = personDetailsService1;
    }

    @Override
    public ResponseEntity<?> authUser(AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(authenticationDTO.getName(), authenticationDTO.getPassword()));
            PersonDetails personDetails = personDetailsService.loadUserByUsername(authenticationDTO.getName());
            String accessToken = JwtTokenUtils.generateAccessToken(personDetails);
            String refreshToken = JwtTokenUtils.generateRefreshToken(personDetails);
            personDetails.getPerson().setRefreshToken(refreshToken);
            return ResponseEntity.ok(new JWTResponse(accessToken,refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }
}
