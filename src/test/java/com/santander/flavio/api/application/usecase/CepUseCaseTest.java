package com.santander.flavio.api.application.usecase;

import com.santander.flavio.api.application.domain.Cep;
import com.santander.flavio.api.application.ports.out.CepOutPort;
import com.santander.flavio.api.application.ports.out.ViaCepOutPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CepUseCaseTest {

    @Mock
    private CepOutPort cepOutPort;

    @Mock
    private ViaCepOutPort viaCepOutPort;

    @InjectMocks
    private CepUseCase cepUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultar_CepEncontradoNoBanco() {
        // Cenário: CEP já está presente no banco
        Long cep = 12345678L;
        Cep cepEncontrado = new Cep();
        cepEncontrado.setNumero(cep);
        cepEncontrado.setLogradouro("Rua Exemplo");

        // Mock do repositório para retornar o CEP
        when(cepOutPort.obter(cep)).thenReturn(cepEncontrado);

        // Execução
        Cep resultado = cepUseCase.consultar(cep);

        // Verificações
        assertNotNull(resultado);
        assertEquals(cep, resultado.getNumero());
        assertEquals("Rua Exemplo", resultado.getLogradouro());
        verify(cepOutPort, times(1)).obter(cep);
        verify(viaCepOutPort, never()).obter(any());
    }

    @Test
    void consultar_CepNaoEncontradoFazRequisicaoApiExterna() {
        // Cenário: CEP não está presente no banco, mas é obtido via API externa
        Long cep = 12345678L;
        Cep cepObtido = new Cep();
        cepObtido.setLogradouro("Rua API");
        cepObtido.setBairro("Bairro API");

        // Mock para retornar null no repositório e retornar dados na API
        when(cepOutPort.obter(cep)).thenReturn(null);
        when(viaCepOutPort.obter(cep)).thenReturn(cepObtido);

        // Execução
        Cep resultado = cepUseCase.consultar(cep);

        // Verificações
        assertNotNull(resultado);
        assertEquals(cep, resultado.getNumero());
        assertEquals("Rua API", resultado.getLogradouro());
        assertEquals("Bairro API", resultado.getBairro());
        verify(cepOutPort, times(1)).obter(cep);
        verify(viaCepOutPort, times(1)).obter(cep);
        verify(cepOutPort, times(1)).salvar(cepObtido);
    }
}