package com.example.office365wopi.exception;

public class UnSupportedRequestException extends RuntimeException {
    public UnSupportedRequestException(String message) {
        super(message);
    }
}
