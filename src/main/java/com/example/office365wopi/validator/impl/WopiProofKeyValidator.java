package com.example.office365wopi.validator.impl;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WopiProofKeyValidator implements WopiValidator {
    @Override
    public boolean validate(HttpServletRequest request) {
        /**
         * TODO: implement it based on the following docs
         * https://wopi.readthedocs.io/en/latest/discovery.html
         * https://wopi.readthedocs.io/en/latest/scenarios/proofkeys.html
         */
        return true;
    }
}
