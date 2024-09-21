package com.cristianml.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

// This class is for backend don't throw so much information about exception errors
public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;

}
