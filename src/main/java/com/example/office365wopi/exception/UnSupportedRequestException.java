package com.example.office365wopi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED, reason = "Operation not supported")
public class UnSupportedRequestException extends RuntimeException {
    public UnSupportedRequestException(String message) {
        super(message);
    }
}
