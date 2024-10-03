package com.cristianml.reservation.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The TokenProvider class is responsible for handling JWT (JSON Web Token) operations
 * such as token creation, validation, and extraction of authentication information.
 *
 * - It generates tokens for authenticated users, including information like username and role.
 * - It validates tokens to ensure integrity and expiration are correct.
 * - It extracts user details from the token for authentication purposes.
 *
 * This class plays a crucial role in securing the application by enabling stateless
 * authentication through the use of JWTs.
 */

@Component
public class TokenProvider {

    // Injects the JWT secret value from the configuration.
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Injects the validity duration of the JWT token in seconds.
    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;

    // Stores the signing key derived from the JWT secret.
    private Key key;

    // Used to parse and validate JWT tokens.
    private JwtParser jwtParser;

    // Initializes the signing key and the JWT parser.
    // The key is derived from the configured secret and is used for signing and verifying JWT tokens.
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    // Generate JWT tokens:
    // When a user is authenticated, the TokenProvider creates a JWT token that includes
    // user information like username and role.
    // This token is used to identify and authenticate the user in subsequent requests
    // without requiring the user to log in again.
    public String createAccessToken(Authentication authentication) {
        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getAuthority();

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000))
                .compact();
    }

    // Retrieve authentication:
    // Extracts information from the JWT token, such as username and role,
    // and creates an authentication object used to verify the user's permissions
    // within the application.
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String role = claims.get("role").toString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // Validate JWT tokens:
    // Upon receiving a request, the TokenProvider checks the validity of the JWT token.
    // This includes ensuring the token has not been tampered with and has not expired.
    // If the token is valid, user authentication information is extracted from the token.
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}