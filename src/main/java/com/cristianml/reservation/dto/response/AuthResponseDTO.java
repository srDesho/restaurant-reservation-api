package com.cristianml.reservation.dto.response;

import lombok.Data;

// Represents the response data for authentication, including a JWT token and user profile information.

@Data
public class AuthResponseDTO {
    private String token;
    private UserProfileResponseDTO user;
}
