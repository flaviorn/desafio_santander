package com.santander.flavio.api.adapters.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private HttpStatus statusCode;

    public ApplicationException(String message) {
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApplicationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApplicationException(String message, HttpStatus statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode () {
        return this.statusCode;
    }
}
