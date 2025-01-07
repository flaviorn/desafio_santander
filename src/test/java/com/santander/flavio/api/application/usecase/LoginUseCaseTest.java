package com.santander.flavio.api.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.santander.flavio.api.application.domain.Client;
import com.santander.flavio.api.application.ports.out.ClientOutPort;

public class LoginUseCaseTest {

    @Mock
    private ClientOutPort clientOutPort;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private static final String SECRET = "testSecretKey";
    private static final String ISSUER = "testIssuer";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(loginUseCase, "secret", SECRET);
        ReflectionTestUtils.setField(loginUseCase, "issuer", ISSUER);
    }

    @Test
    void testLogarComSucesso() {
        // Arrange
        String clientId = "testClient";
        String clientSecret = "testSecret";

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);

        doNothing().when(clientOutPort).autenticar(client);

        // Act
        String token = loginUseCase.logar(client);

        // Assert
        assertNotNull(token);

        DecodedJWT decodedJWT = JWT.decode(token);
        assertEquals(ISSUER, decodedJWT.getIssuer());
        assertEquals(clientId, decodedJWT.getSubject());
        assertTrue(decodedJWT.getExpiresAt().after(new Date()));

        verify(clientOutPort, times(1)).autenticar(client);
    }

    @Test
    void testLogarFalhaAutenticacao() {
        // Arrange
        String clientId = "testClient";
        String clientSecret = "testSecret";

        Client client = new Client();
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);

        doThrow(new RuntimeException("Falha na autenticação")).when(clientOutPort).autenticar(client);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> loginUseCase.logar(client));
        assertEquals("Falha na autenticação", exception.getMessage());

        verify(clientOutPort, times(1)).autenticar(client);
    }
}
