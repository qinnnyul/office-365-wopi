package com.example.office365wopi.exception;

public class LockMismatchException extends RuntimeException {
    private String existingLock;
    private String reason;

    public LockMismatchException(String existingLock, String reason) {
        this.existingLock = existingLock;
        this.reason = reason;
    }

    public String getExistingLock() {
        return existingLock;
    }

    public String getReason() {
        return reason;
    }
}
