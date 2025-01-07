package com.santander.flavio.api.adapters.out.mySql.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.santander.flavio.api.adapters.exceptions.AuthenticationException;
import com.santander.flavio.api.application.domain.Client;

public class ClientMySqlTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientMySql clientMySql;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testAutenticarComSucesso() {
        // Arrange
        String clientId = "testClient";
        String rawSecret = "testSecret";
        String encodedSecret = passwordEncoder.encode(rawSecret);

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(rawSecret);

        ClientEntity mockEntity = new ClientEntity();
        mockEntity.setClientId(clientId);
        mockEntity.setClientSecret(encodedSecret);

        when(clientRepository.findByClientId(clientId)).thenReturn(mockEntity);

        // Act & Assert
        assertDoesNotThrow(() -> clientMySql.autenticar(client));

        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    @Test
    void testAutenticarComCredenciaisInvalidas() {
        // Arrange
        String clientId = "testClient";
        String rawSecret = "testSecret";

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(rawSecret);

        ClientEntity mockEntity = new ClientEntity();
        mockEntity.setClientId(clientId);
        mockEntity.setClientSecret(passwordEncoder.encode("wrongSecret"));

        when(clientRepository.findByClientId(clientId)).thenReturn(mockEntity);

        // Act
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> clientMySql.autenticar(client));

        // Assert
        assertEquals("Credenciais invalidas", exception.getMessage());
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    @Test
    void testAutenticarComUsuarioNaoEncontrado() {
        // Arrange
        String clientId = "testClient";
        String rawSecret = "testSecret";

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(rawSecret);

        when(clientRepository.findByClientId(clientId)).thenReturn(null);

        // Act
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> clientMySql.autenticar(client));

        // Assert
        assertEquals("Credenciais invalidas", exception.getMessage());
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    @Test
    void testAutenticarComExcecaoNoBanco() {
        // Arrange
        String clientId = "testClient";
        String rawSecret = "testSecret";

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(rawSecret);

        when(clientRepository.findByClientId(clientId)).thenThrow(new DataAccessException("Erro no banco") {});

        // Act
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> clientMySql.autenticar(client));

        // Assert
        assertEquals("Falha na autenticacao", exception.getMessage());
        verify(clientRepository, times(1)).findByClientId(clientId);
    }
}
