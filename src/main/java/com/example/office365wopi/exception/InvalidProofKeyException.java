package com.example.office365wopi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid Proof Key")
public class InvalidProofKeyException extends RuntimeException {

    public InvalidProofKeyException(String message) {
        super(message);
    }
}
