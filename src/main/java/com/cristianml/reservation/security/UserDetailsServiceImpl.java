package com.cristianml.reservation.security;

import com.cristianml.reservation.domain.entity.User;
import com.cristianml.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Searches for the user in the database by email and throws an exception if not found.
        User user = userRepository
                .findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Returns user details for Spring Security, including username, password, and roles.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}