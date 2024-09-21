package com.cristianml.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

// This class handles global exceptions and returns appropriate error responses in the event of exceptions.

@ControllerAdvice // This annotation defines the class as a global exception handler for all controllers.
public class GlobalExceptionHandler {

    // This method handles ResourceNotFoundException and returns a custom error response with NOT_FOUND status.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // Creating an ErrorDetails object with the current timestamp, exception message, and request description.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        // Returning a response with the error details and the appropriate HTTP status (404 Not Found).
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // This method handles BadRequestException and returns a custom error response with BAD_REQUEST status.
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleResourceBadRequestException(BadRequestException ex, WebRequest request) {
        // Creating an ErrorDetails object with the current timestamp, exception message, and request description.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        // Returning a response with the error details and the appropriate HTTP status (400 Bad Request).
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // This method handles any general exception and returns a custom error response with INTERNAL_SERVER_ERROR status.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(BadRequestException ex, WebRequest request) {
        // Creating an ErrorDetails object with the current timestamp, exception message, and request description.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        // Returning a response with the error details and the appropriate HTTP status (500 Internal Server Error).
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
