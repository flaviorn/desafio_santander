package com.santander.flavio.api.adapters.out.api.viaCep;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.santander.flavio.api.adapters.exceptions.RequestException;
import com.santander.flavio.api.application.domain.Cep;
import com.santander.flavio.api.application.ports.out.LogOutPort;
import com.santander.flavio.api.application.ports.out.ViaCepOutPort;

@Service
public class ViaCepApi implements ViaCepOutPort{

    private static final Logger logger = LoggerFactory.getLogger(ViaCepApi.class);

    @Value("${api.viacep.url}")
    private String viacepUrl;

    private final RestTemplate restTemplate;

    private LogOutPort logOutPort;

    public ViaCepApi(RestTemplate restTemplate, LogOutPort logOutPort) {
        this.restTemplate = restTemplate;
        this.logOutPort = logOutPort;
    }

    public Cep obter(Long cep) {
        DecimalFormat df = new DecimalFormat("00000000");
        String url = String.format("%s%s/json", viacepUrl, df.format(cep));
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);

        try {
            // Loga a requisição
            String requestLog = String.format("Request: method: GET url: %s", url);
            logger.info(requestLog);

            ResponseEntity<Cep> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Cep.class
            );

            // Loga a resposta
            String responseLog = String.format("Response: statusCode=%s headers=%s body=%s", response.getStatusCode(), response.getHeaders(), response.getBody());
            logger.info(responseLog);
            logOutPort.salvar(String.join(" ", requestLog, responseLog));

            return response.getBody();
        } catch (Exception e) {
            // Log de erro
            logger.error("Erro ao chamar a API externa: {}", e.getMessage(), e);
            throw new RequestException("Erro ao chamar a API externa");
        }
    }
}
