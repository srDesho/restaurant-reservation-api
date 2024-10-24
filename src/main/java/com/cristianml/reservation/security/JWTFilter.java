package com.cristianml.reservation.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

// This filter is crucial for managing the application's security.
// It ensures that each HTTP request is properly authenticated and authorized using JWT tokens.
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // Extract the JWT token: Retrieves the token from the Authorization header of the HTTP request.
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // Validate and process the token: Checks if the token has the correct format ("Bearer ")
        // and, if valid, retrieves the user's authentication associated with the token.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            // Sets the authentication in the Spring Security context
            // to enable authorization based on the JWT token.
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continues with the execution of the Spring Security filter chain.
        chain.doFilter(request, response);
    }
}