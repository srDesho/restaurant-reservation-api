package com.cristianml.reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// The JWTConfigurer class integrates the JWTFilter into Spring Security's security cycle.
// This ensures that HTTP requests are authenticated using JWT tokens before being processed,
// adding an extra layer of security to protect application resources.

// The @RequiredArgsConstructor annotation generates a constructor for final fields,
// simplifying dependency injection for the TokenProvider.

// This class extends SecurityConfigurerAdapter to allow adding custom filters
// to the application's security flow.
@RequiredArgsConstructor
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Creates an instance of JWTFilter, which uses the TokenProvider to validate JWT tokens.
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);

        // Adds the JWTFilter to Spring Security's filter chain, placing it before the standard
        // UsernamePasswordAuthenticationFilter. This ensures that JWT tokens are processed
        // before handling any username-password-based authentication.
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
