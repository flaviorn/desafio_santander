package com.santander.flavio.api.adapters.exceptions;

import org.springframework.http.HttpStatus;

public class RequestException extends ApplicationException {

    public RequestException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }
    
    public RequestException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_GATEWAY, cause);
    }
}
