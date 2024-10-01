package com.cristianml.reservation.repository;

import com.cristianml.reservation.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Retrieves a single User by email. Returns Optional to handle cases where the user might not exist.
    Optional<User> findOneByEmail(String email);

    // Checks if a User exists in the database with the given email.
    boolean existsByEmail(String email);

}
