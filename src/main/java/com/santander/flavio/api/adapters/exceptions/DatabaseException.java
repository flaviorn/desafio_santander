package com.santander.flavio.api.adapters.exceptions;

import org.springframework.http.HttpStatus;

public class DatabaseException extends ApplicationException {

    public DatabaseException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message,HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
