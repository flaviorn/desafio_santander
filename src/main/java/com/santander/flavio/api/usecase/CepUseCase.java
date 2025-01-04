package com.santander.flavio.api.usecase;

import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santander.flavio.api.domain.Cep;
import com.santander.flavio.api.repository.CepRepository;
import com.santander.flavio.api.service.ViaCepService;

@Component
public class CepUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CepUseCase.class);

    @Autowired
    private CepRepository repository;

    @Autowired
    private ViaCepService viaCepService;

    public Cep consultar(Long cep) {
        logger.info("Recebida nova requisição!!! Cep={}", cep);
        // Verifica se o cep já se encontra no banco
        Cep cepEncontrado = repository.findByNumero(cep);

        // Se o cep estiver vazio, faz requisicao para a api externa e salva no banco
        if (ObjectUtils.isEmpty(cepEncontrado)) {
            logger.info("Cep não encontrado, será obtido dados no ViaCep.");

            Cep cepObtido = viaCepService.getCepInfo(cep);
            cepObtido.setNumero(cep);
            cepObtido.setData(LocalDateTime.now());
            repository.save(cepObtido);

            return cepObtido;

        // Senao, retorna os dados retornados do banco
        } else {
            logger.info("Cep encontrado. Data do dado obtido={}", cepEncontrado.getData());
            return cepEncontrado;
        }        
    }    
}
