package com.santander.flavio.api.adapters.out.mySql.client;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.santander.flavio.api.adapters.exceptions.AuthenticationException;
import com.santander.flavio.api.application.domain.Client;
import com.santander.flavio.api.application.ports.out.ClientOutPort;

@Repository
public class ClientMySql implements ClientOutPort {

    private static final Logger logger = LoggerFactory.getLogger(ClientMySql.class);

    private final ClientRepository clientRepository;

    public ClientMySql(ClientRepository clientRepository) {
        logger.debug("Construtor");
        this.clientRepository = clientRepository;
    }

    public void autenticar (Client client) {
        logger.debug("Funcao autenticar, parametros: client={}", client);
        try {
            ClientEntity clientEntity = clientRepository.findByClientId(client.getClientId());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            if (clientEntity == null || !passwordEncoder.matches(client.getClientSecret(), clientEntity.getClientSecret())) {
                // retorna erro se falhar na autenticacao
                throw new AuthenticationException("Credenciais invalidas");
            }
        } catch (DataAccessException ex) {
            throw new AuthenticationException("Falha na autenticacao", ex);
        }
    }    
}
