package com.example.office365wopi.service;

import com.example.office365wopi.validator.WopiAuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class WopiLockService {
    private WopiAuthenticationValidator validator;

    @Autowired
    public WopiLockService(WopiAuthenticationValidator validator) {
        this.validator = validator;
    }

    public ResponseEntity handleLockRequest(String name, HttpServletRequest request) {
        this.validator.validate(request);
        return null;
    }

    public ResponseEntity handleUnLockRequest(String name, HttpServletRequest request) {
        this.validator.validate(request);
        return null;
    }

    public ResponseEntity handleRefreshLockRequest(String name, HttpServletRequest request) {
        this.validator.validate(request);
        return null;
    }

    public ResponseEntity handleUnlockAndRelockRequest(String name, HttpServletRequest request) {
        this.validator.validate(request);
        return null;
    }
}
