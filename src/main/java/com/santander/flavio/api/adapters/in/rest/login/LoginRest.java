package com.santander.flavio.api.adapters.in.rest.login;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.flavio.api.adapters.in.rest.login.model.LoginRequest;
import com.santander.flavio.api.adapters.in.rest.login.model.LoginResponse;
import com.santander.flavio.api.application.domain.Client;
import com.santander.flavio.api.application.ports.in.LoginInPort;


@RestController
@RequestMapping("/login")
public class LoginRest {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginRest.class);

    @Autowired
    private LoginInPort loginInPort;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<LoginResponse> obterToken(@RequestBody @Valid LoginRequest login) {
        logger.debug("Funcao obterToken, parametros: login={}", login);
        return ResponseEntity.ok(new LoginResponse(loginInPort.logar(modelMapper.map(login, Client.class))));
    }
}
