package com.example.office365wopi.validator;

import com.example.office365wopi.exception.InvalidAccessTokenException;
import com.example.office365wopi.exception.InvalidProofKeyException;
import com.example.office365wopi.validator.impl.WopiAccessTokenValidator;
import com.example.office365wopi.validator.impl.WopiProofKeyValidator;
import com.example.office365wopi.validator.impl.WopiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class WopiAuthenticationValidator {
    private List<WopiValidator> wopiValidators;

    @Autowired
    public WopiAuthenticationValidator(List<WopiValidator> wopiValidators) {
        this.wopiValidators = wopiValidators;
    }

    public void validate(HttpServletRequest request) {
        wopiValidators.forEach(wopiValidator -> {
            if (!wopiValidator.validate(request)) {
                if (wopiValidator instanceof WopiAccessTokenValidator) {
                    throw new InvalidAccessTokenException("Invalid access token");
                }
                if (wopiValidator instanceof WopiProofKeyValidator) {
                    throw new InvalidProofKeyException("Invalid Proof Key");
                }
            }
        });
    }
}
