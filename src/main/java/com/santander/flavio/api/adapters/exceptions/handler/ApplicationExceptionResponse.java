package com.santander.flavio.api.adapters.exceptions.handler;

import lombok.Getter;

@Getter
public class ApplicationExceptionResponse {
    private String message;
    private int code;

    public ApplicationExceptionResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
