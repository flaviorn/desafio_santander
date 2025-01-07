package com.santander.flavio.api.adapters.in.rest.cep.model;

import com.santander.flavio.api.application.domain.Cep;

import lombok.Getter;

@Getter
public class CepResponse {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    
    public CepResponse(Cep cep) {
        this.cep = cep.getCep();

        this.logradouro = cep.getLogradouro();
        this.complemento = cep.getComplemento();
        this.unidade = cep.getUnidade();
        this.bairro = cep.getBairro();
        this.localidade = cep.getLocalidade();
        this.uf = cep.getUf();
        this.estado = cep.getEstado();
        this.regiao = cep.getRegiao();
        this.ibge = cep.getIbge();
        this.gia = cep.getGia();
        this.ddd = cep.getDdd();
        this.siafi = cep.getSiafi();
    }
}
