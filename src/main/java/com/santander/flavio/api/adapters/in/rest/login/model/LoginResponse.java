package com.santander.flavio.api.adapters.in.rest.login.model;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String token;
    
    public LoginResponse(String token) {
        this.token = token;
    }
}
