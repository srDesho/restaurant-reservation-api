package com.cristianml.reservation.config;

import com.cristianml.reservation.security.JWTConfigurer;
import com.cristianml.reservation.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    // Defines the security configuration for the application.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configures CORS (Cross-Origin Resource Sharing) with default settings,
                // allowing the web application to securely make requests to other domains.
                // CORS controls how web resources are requested from different origins.
                .cors(Customizer.withDefaults())

                // Disables CSRF (Cross-Site Request Forgery) protection,
                // a security measure that prevents malicious sites from sending unauthorized requests on behalf of a user.
                // CSRF is unnecessary in applications using JWT tokens for authentication.
                .csrf(AbstractHttpConfigurer::disable)

                // Configures request authorization rules.
                .authorizeHttpRequests((authz) -> authz
                        // Allows unauthenticated access to registration and authentication routes.
                        .requestMatchers("/auth/sign-up", "/auth/sign-in").permitAll()
                        // Allows unauthenticated access to Swagger documentation routes.
                        .requestMatchers("/api/v1/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                        // Requires authentication for any other request.
                        .anyRequest()
                        .authenticated()
                )

                // Configures session management to ensure sessions are not stored on the server.
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Adds the JWTConfigurer to handle authentication based on JWT tokens.
                .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());

        return http.build();
    }

    // Defines the password encoder used to encrypt user passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}