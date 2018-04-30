package com.example.office365wopi.validator.impl;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WopiAccessTokenValidator implements WopiValidator {
    @Override
    public boolean validate(HttpServletRequest request) {
        /**
         * TODO: implement it based on the following docs
         * https://wopi.readthedocs.io/projects/wopirest/en/latest/concepts.html#term-access-token
         */
        return true;
    }
}
