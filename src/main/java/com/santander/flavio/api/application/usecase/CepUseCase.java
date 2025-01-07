package com.santander.flavio.api.application.usecase;

import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santander.flavio.api.application.domain.Cep;
import com.santander.flavio.api.application.ports.in.CepInPort;
import com.santander.flavio.api.application.ports.out.CepOutPort;
import com.santander.flavio.api.application.ports.out.ViaCepOutPort;

@Component
public class CepUseCase implements CepInPort{

    private static final Logger logger = LoggerFactory.getLogger(CepUseCase.class);

    @Autowired
    private CepOutPort cepOutPort;

    @Autowired
    private ViaCepOutPort viaCepOutPort;

    public Cep consultar(Long cep) {
        logger.debug("Funcao consultar, parametros: cep={}", cep);

        logger.info("Recebida nova requisição!!! cep={}", cep);
        // Verifica se o cep já se encontra no banco
        Cep cepEncontrado = cepOutPort.obter(cep);

        // Se o cep estiver vazio, faz requisicao para a api externa e salva no banco
        if (ObjectUtils.isEmpty(cepEncontrado)) {
            logger.info("Cep não encontrado, será obtido dados no ViaCep.");

            Cep cepObtido = viaCepOutPort.obter(cep);
            cepObtido.setNumero(cep);
            cepObtido.setData(LocalDateTime.now());
            cepOutPort.salvar(cepObtido);

            return cepObtido;

        // Senao, retorna os dados retornados do banco
        } else {
            logger.info("Cep encontrado. Data do dado obtido={}", cepEncontrado.getData());
            return cepEncontrado;
        }

    }    
}
