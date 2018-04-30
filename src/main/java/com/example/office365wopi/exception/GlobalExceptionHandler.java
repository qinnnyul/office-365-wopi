package com.example.office365wopi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType(String.valueOf(MediaType.APPLICATION_JSON_UTF8)))
                .body("Resource not found/user unauthorized");
    }
}
