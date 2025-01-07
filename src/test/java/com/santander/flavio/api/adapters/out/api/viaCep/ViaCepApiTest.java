package com.santander.flavio.api.adapters.out.api.viaCep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import com.santander.flavio.api.adapters.exceptions.RequestException;
import com.santander.flavio.api.application.domain.Cep;
import com.santander.flavio.api.application.ports.out.LogOutPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class ViaCepApiTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private LogOutPort logOutPort;

    @InjectMocks
    private ViaCepApi viaCepApi;

    private static final String VIACEP_URL = "http://viacep.test/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viaCepApi = new ViaCepApi(restTemplate, logOutPort);
        ReflectionTestUtils.setField(viaCepApi, "viacepUrl", VIACEP_URL);
    }

    @Test
    void testObter_Sucesso() {
        // Arrange
        Long cepInput = 12345678L;
        String url = "http://viacep.test/12345678/json";
        Cep mockedCep = new Cep(1L,12345678L, LocalDateTime.now(),"12345678", "logradouro", "complemento", "unidade", "bairro", "localidade", "uf", "estado", "regiao", "ibge", "gia", "ddd", "siafi");
        ResponseEntity<Cep> mockedResponse = ResponseEntity.ok(mockedCep);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Cep.class)))
                .thenReturn(mockedResponse);

        // Act
        Cep result = viaCepApi.obter(cepInput);

        // Assert
        assertNotNull(result);
        assertEquals(mockedCep, result);
        verify(logOutPort, times(1)).salvar(anyString());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Cep.class));
    }

    @Test
    void testObter_FalhaAPI() {
        // Arrange
        Long cepInput = 12345678L;
        String url = "http://viacep.test/12345678/json";

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Cep.class)))
                .thenThrow(new RuntimeException("Erro na API"));

        // Act & Assert
        RequestException exception = assertThrows(RequestException.class, () -> viaCepApi.obter(cepInput));
        assertEquals("Erro ao chamar a API externa", exception.getMessage());
        verify(logOutPort, never()).salvar(anyString());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Cep.class));
    }

    @Test
    void testObter_LogRequisicao() {
        // Arrange
        Long cepInput = 12345678L;
        String url = "http://viacep.test/12345678/json";
        Cep mockedCep = new Cep(1L,12345678L, LocalDateTime.now(),"12345678", "logradouro", "complemento", "unidade", "bairro", "localidade", "uf", "estado", "regiao", "ibge", "gia", "ddd", "siafi");
        ResponseEntity<Cep> mockedResponse = ResponseEntity.ok(mockedCep);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Cep.class)))
                .thenReturn(mockedResponse);

        // Act
        viaCepApi.obter(cepInput);

        // Assert
        verify(logOutPort, times(1)).salvar(contains("Request: method: GET url:"));
        verify(logOutPort, times(1)).salvar(contains("Response: statusCode="));
    }
}
