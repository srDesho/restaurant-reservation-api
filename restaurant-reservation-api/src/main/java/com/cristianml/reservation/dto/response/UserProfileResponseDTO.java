package com.cristianml.reservation.dto.response;

import com.cristianml.reservation.domain.enums.Role;
import lombok.Data;

// Represents the response data for a user profile, including user details like id, first name, last name, email, and role.

@Data
public class UserProfileResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}

