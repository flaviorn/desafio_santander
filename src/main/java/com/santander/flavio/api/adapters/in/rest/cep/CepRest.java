package com.santander.flavio.api.adapters.in.rest.cep;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.flavio.api.adapters.in.rest.cep.model.CepResponse;
import com.santander.flavio.api.application.ports.in.CepInPort;


@RestController
@RequestMapping("/cep")
public class CepRest {
    
    private static final Logger logger = LoggerFactory.getLogger(CepRest.class);

    @Autowired
    private CepInPort cepInPort;

    @GetMapping("/{id}")
    public ResponseEntity<CepResponse> obterCep(@PathVariable Long id) {
        logger.debug("Funcao obterCep, parametros: id={}", id);
        return ResponseEntity.ok(new CepResponse(cepInPort.consultar(id)));
    }
}
