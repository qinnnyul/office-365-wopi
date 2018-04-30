package com.example.office365wopi.validator.impl;

import javax.servlet.http.HttpServletRequest;

public interface WopiValidator {
    boolean validate(HttpServletRequest request);
}
