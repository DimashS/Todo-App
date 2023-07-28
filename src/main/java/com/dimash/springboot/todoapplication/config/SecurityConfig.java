package com.dimash.springboot.todoapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((AbstractHttpConfigurer::disable));
        httpSecurity.cors((AbstractHttpConfigurer::disable));
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/auth/registration",
                                "/auth/login",
                                "/auth/token",
                                "/swagger-ui/**")
                        .permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .addFilterAfter(jwtFilter, JwtFilter.class)
        );
        httpSecurity.formLogin(httpSecurityFormLoginConfigurer ->
                httpSecurityFormLoginConfigurer.loginPage("/auth/login")
                        .loginProcessingUrl("/login").defaultSuccessUrl("/api/v1/hello"));
        httpSecurity.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/exit")
                .logoutSuccessUrl("/auth/login"));
        return httpSecurity.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
