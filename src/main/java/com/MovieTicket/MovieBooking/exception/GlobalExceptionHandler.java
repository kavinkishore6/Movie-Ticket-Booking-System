package com.MovieTicket.MovieBooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===================== CUSTOM EXCEPTIONS =====================

    public static class BookingNotFoundException extends RuntimeException {
        public BookingNotFoundException(String message) {
            super(message);
        }
    }

    public static class MovieNotFoundException extends RuntimeException {
        public MovieNotFoundException(String message) {
            super(message);
        }
    }

    public static class ShowNotFoundException extends RuntimeException {
        public ShowNotFoundException(String message) {
            super(message);
        }
    }

    public static class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}


    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // ===================== HANDLERS =====================

    @ExceptionHandler({
            BookingNotFoundException.class,
            MovieNotFoundException.class,
            ShowNotFoundException.class,
            UserNotFoundException.class,
             PaymentException.class 
    })
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
