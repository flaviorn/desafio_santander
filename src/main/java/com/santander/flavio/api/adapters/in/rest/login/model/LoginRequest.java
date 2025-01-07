package com.santander.flavio.api.adapters.in.rest.login.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;
}
