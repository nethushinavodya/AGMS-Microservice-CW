package com.agms.zone_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTemperatureException.class)
    public ResponseEntity<String> handleTemp(InvalidTemperatureException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}