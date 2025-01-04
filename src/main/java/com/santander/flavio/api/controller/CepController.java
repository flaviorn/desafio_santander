package com.santander.flavio.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.flavio.api.dtos.CepResponseDto;
import com.santander.flavio.api.usecase.CepUseCase;


@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private CepUseCase cepUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<CepResponseDto> obterCep(@PathVariable Long id) {
        return ResponseEntity.ok(new CepResponseDto(cepUseCase.consultar(id)));
    }
}
