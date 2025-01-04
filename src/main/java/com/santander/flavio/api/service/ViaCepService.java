package com.santander.flavio.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.santander.flavio.api.domain.Cep;

@Service
public class ViaCepService {

    private String viaCepUrl;

    private final WebClient webClient;

    public ViaCepService(WebClient.Builder webClientBuilder) {
        this.viaCepUrl = "http://localhost:9090";
        this.webClient = webClientBuilder.baseUrl(viaCepUrl).build();
    }

    public Cep getCepInfo(Long cep) {
        return webClient.get()
                        .uri("/{cep}/json", cep)
                        .retrieve()
                        .bodyToMono(Cep.class)
                        .block();
    }
}
