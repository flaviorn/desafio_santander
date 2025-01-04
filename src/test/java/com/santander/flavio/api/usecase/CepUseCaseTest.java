package com.santander.flavio.api.usecase;

import com.santander.flavio.api.domain.Cep;
import com.santander.flavio.api.repository.CepRepository;
import com.santander.flavio.api.service.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CepUseCaseTest {

    @Mock
    private CepRepository repository;

    @Mock
    private ViaCepService viaCepService;

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
        when(repository.findByNumero(cep)).thenReturn(cepEncontrado);

        // Execução
        Cep resultado = cepUseCase.consultar(cep);

        // Verificações
        assertNotNull(resultado);
        assertEquals(cep, resultado.getNumero());
        assertEquals("Rua Exemplo", resultado.getLogradouro());
        verify(repository, times(1)).findByNumero(cep);
        verify(viaCepService, never()).getCepInfo(any());
    }

    @Test
    void consultar_CepNaoEncontradoFazRequisicaoApiExterna() {
        // Cenário: CEP não está presente no banco, mas é obtido via API externa
        Long cep = 12345678L;
        Cep cepObtido = new Cep();
        cepObtido.setLogradouro("Rua API");
        cepObtido.setBairro("Bairro API");

        // Mock para retornar null no repositório e retornar dados na API
        when(repository.findByNumero(cep)).thenReturn(null);
        when(viaCepService.getCepInfo(cep)).thenReturn(cepObtido);

        // Execução
        Cep resultado = cepUseCase.consultar(cep);

        // Verificações
        assertNotNull(resultado);
        assertEquals(cep, resultado.getNumero());
        assertEquals("Rua API", resultado.getLogradouro());
        assertEquals("Bairro API", resultado.getBairro());
        verify(repository, times(1)).findByNumero(cep);
        verify(viaCepService, times(1)).getCepInfo(cep);
        verify(repository, times(1)).save(cepObtido);
    }
}