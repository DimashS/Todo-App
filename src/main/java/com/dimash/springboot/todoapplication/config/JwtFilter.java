package com.dimash.springboot.todoapplication.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dimash.springboot.todoapplication.service.PersonDetailsService;
import com.dimash.springboot.todoapplication.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {


    @Value("${jwt_access_secret}")
    private String secretKeyBASE64;
    private final JwtTokenUtils jwtTokenUtils;
    private final PersonDetailsService personDetailsService;

    public JwtFilter(JwtTokenUtils jwtTokenUtils, PersonDetailsService personDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.personDetailsService = personDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && !header.isBlank() && header.startsWith("Bearer ")) {
            String authHeaderToken = extractToken(request);
            if (authHeaderToken.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT");
            } else {
                try {
                    String username = jwtTokenUtils.validateAndRetrieveClaim(authHeaderToken, secretKeyBASE64);
                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (ExpiredJwtException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT token has expired");
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
