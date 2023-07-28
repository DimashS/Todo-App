package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.response.AuthResponse;
import com.dimash.springboot.todoapplication.security.PersonDetails;
import com.dimash.springboot.todoapplication.service.AuthService;
import com.dimash.springboot.todoapplication.service.PersonDetailsService;
import com.dimash.springboot.todoapplication.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PersonDetailsService personDetailsService;
    private final JWTUtil jwtUtil;
    private final PersonRepository personRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PersonDetailsService personDetailsService, JWTUtil jwtUtil, PersonRepository personRepository) {
        this.authenticationManager = authenticationManager;
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse authUser(String name, String password) {
        try {
            PersonDetails personDetails = personDetailsService.loadUserByUsername(name);
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(personDetails, password));
            if (authentication.isAuthenticated()) {
                String accessToken = jwtUtil.generateAccessToken(personDetails);
                String refreshToken = jwtUtil.generateRefreshToken(personDetails);
                Person person = personDetails.getPerson();
                person.setRefreshToken(refreshToken);
                return new AuthResponse("Authorization successful!", accessToken, refreshToken);
            }
            return null;
        } catch (AuthenticationException e) {
            return new AuthResponse("Authorization failed!", null, null);
        }
    }

    public AuthResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            Claims claims = jwtUtil.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
            Person person = personDetails.getPerson();
            if (person.getRefreshToken() != null && person.getRefreshToken().equals(refreshToken)) {
                String accessToken = jwtUtil.generateRefreshToken(personDetails);
                return new AuthResponse("Get your access token!", accessToken, null);
            } else {
                return new AuthResponse("Something wrong, try again later!", null, null);
            }
        }
        return new AuthResponse(null, null, null);
    }

    public AuthResponse getRefreshToken(@NonNull String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            Claims claims = jwtUtil.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
            Person person = personDetails.getPerson();
            if (person.getRefreshToken() != null && person.getRefreshToken().equals(refreshToken)) {
                String newAccessToken = jwtUtil.generateAccessToken(personDetails);
                String newRefreshToken = jwtUtil.generateRefreshToken(personDetails);
                person.setRefreshToken(newRefreshToken);
                return new AuthResponse("Get your new access token and refresh token!"
                        , newAccessToken, newRefreshToken);
            } else {
                return new AuthResponse("Something wrong, try again later!", null, null);
            }
        }
        return new AuthResponse(null, null, null);
    }

}
//            boolean isPasswordMatched = new BCryptPasswordEncoder().matches(
//                    password, personDetails.getPassword());
//            if (isPasswordMatched) {
