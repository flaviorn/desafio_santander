package com.santander.flavio.api.application.usecase;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.santander.flavio.api.application.domain.Client;
import com.santander.flavio.api.application.ports.in.LoginInPort;
import com.santander.flavio.api.application.ports.out.ClientOutPort;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class LoginUseCase implements LoginInPort{

    private static final Logger logger = LoggerFactory.getLogger(CepUseCase.class);

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Autowired
    private ClientOutPort clientOutPort;

    public String logar(Client client) {
        logger.debug("Funcao logar, parametros: client={}", client);

        // Verifica dados do client estao corretos
        clientOutPort.autenticar(client);

        // Retornar o token
        return gerarToken(client);
    }

    private String gerarToken(Client client) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withIssuer(issuer)
            .withSubject(client.getClientId())
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Token expira em 1 hora
            .sign(algorithm);
    }
}
