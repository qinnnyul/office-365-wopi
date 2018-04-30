package com.example.office365wopi.exception;

import com.example.office365wopi.request.WopiRequestHeader;
import org.springframework.http.HttpHeaders;
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

    @ExceptionHandler(value = LockMismatchException.class)
    public ResponseEntity handleLockMismatchException(LockMismatchException ex){
        HttpHeaders headers = new HttpHeaders();
        headers.set(WopiRequestHeader.LOCK.getName(), ex.getExistingLock());
        headers.set(WopiRequestHeader.LOCK_FAILURE_REASON.getName(), ex.getReason());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .headers(headers)
                .contentType(MediaType.parseMediaType(String.valueOf(MediaType.APPLICATION_JSON_UTF8)))
                .body("Lock mismatch/Locked by another interface");
    }
}
