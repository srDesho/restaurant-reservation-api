package com.cristianml.reservation.dto.request;

import lombok.Data;

// Represents the data sent by the client for authentication, such as email and password.

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
