package com.example.user_ingredient_service.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordNotCorrectException extends AuthenticationException {

    public PasswordNotCorrectException(String message) {
        super(message);
    }

}
