package com.dimash.springboot.todoapplication.config;

import com.dimash.springboot.todoapplication.security.PersonDetails;
import com.dimash.springboot.todoapplication.service.PersonDetailsService;
import com.dimash.springboot.todoapplication.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// просто для реализации своих фильтров
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getToken((HttpServletRequest) request);
        if (token != null && jwtUtil.validateAccessToken(token)) {
            Claims claims = jwtUtil.getAccessClaims(token);
            String name = claims.getSubject();
            PersonDetails personDetails = personDetailsService.loadUserByUsername(name);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(personDetails.getPerson(), null,
                            personDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request, response);
        }
    }

    private String getToken(HttpServletRequest request) {
        String bearer = request.getHeader("AUTHORIZATION");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
