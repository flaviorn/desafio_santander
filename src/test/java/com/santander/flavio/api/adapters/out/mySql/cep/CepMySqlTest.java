package com.santander.flavio.api.adapters.out.mySql.cep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;

import com.santander.flavio.api.adapters.exceptions.DatabaseException;
import com.santander.flavio.api.application.domain.Cep;

public class CepMySqlTest {

    @Mock
    private CepRepository cepRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CepMySql cepMySql;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterComSucesso() {
        // Arrange
        Long cepNumero = 12345678L;
        CepEntity mockEntity = new CepEntity();
        mockEntity.setNumero(cepNumero);
        Cep mockCep = new Cep();
        mockCep.setNumero(cepNumero);

        when(cepRepository.findByNumero(cepNumero)).thenReturn(mockEntity);
        when(modelMapper.map(mockEntity, Cep.class)).thenReturn(mockCep);

        // Act
        Cep result = cepMySql.obter(cepNumero);

        // Assert
        assertNotNull(result);
        assertEquals(cepNumero, result.getNumero());
        verify(cepRepository, times(1)).findByNumero(cepNumero);
        verify(modelMapper, times(1)).map(mockEntity, Cep.class);
    }

    @Test
    void testObterComCepNaoEncontrado() {
        // Arrange
        Long cepNumero = 12345678L;

        when(cepRepository.findByNumero(cepNumero)).thenReturn(null);

        // Act
        Cep result = cepMySql.obter(cepNumero);

        // Assert
        assertNull(result);
        verify(cepRepository, times(1)).findByNumero(cepNumero);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testObterComExcecao() {
        // Arrange
        Long cepNumero = 12345678L;

        when(cepRepository.findByNumero(cepNumero)).thenThrow(new DataAccessException("Erro no banco") {});

        // Act & Assert
        assertThrows(DatabaseException.class, () -> cepMySql.obter(cepNumero));

        verify(cepRepository, times(1)).findByNumero(cepNumero);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testSalvarComSucesso() {
        // Arrange
        Cep mockCep = new Cep();
        mockCep.setNumero(12345678L);
        CepEntity mockEntity = new CepEntity();

        when(modelMapper.map(mockCep, CepEntity.class)).thenReturn(mockEntity);

        // Act & Assert
        assertDoesNotThrow(() -> cepMySql.salvar(mockCep));

        verify(cepRepository, times(1)).save(mockEntity);
        verify(modelMapper, times(1)).map(mockCep, CepEntity.class);
    }

    @Test
    void testSalvarComExcecao() {
        // Arrange
        Cep mockCep = new Cep();
        mockCep.setNumero(12345678L);

        when(modelMapper.map(mockCep, CepEntity.class)).thenThrow(new DataAccessException("Erro no banco") {});

        // Act & Assert
        assertThrows(DatabaseException.class, () -> cepMySql.salvar(mockCep));

        verifyNoInteractions(cepRepository);
        verify(modelMapper, times(1)).map(mockCep, CepEntity.class);
    }
}