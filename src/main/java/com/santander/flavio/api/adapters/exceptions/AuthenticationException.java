package com.santander.flavio.api.adapters.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends ApplicationException {

    public AuthenticationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, HttpStatus.UNAUTHORIZED, cause);
    }
}
