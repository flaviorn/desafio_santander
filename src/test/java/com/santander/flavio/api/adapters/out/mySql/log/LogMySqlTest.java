package com.santander.flavio.api.adapters.out.mySql.log;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import com.santander.flavio.api.adapters.exceptions.DatabaseException;

public class LogMySqlTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogMySql logMySql;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarComSucesso() {
        // Arrange
        String logMessage = "Teste de log";

        // Act & Assert
        assertDoesNotThrow(() -> logMySql.salvar(logMessage));

        verify(logRepository, times(1)).save(any(LogEntity.class));
    }

    @Test
    void testSalvarComExcecaoNoBanco() {
        // Arrange
        String logMessage = "Teste de log";

        doThrow(new DataAccessException("Erro no banco") {}).when(logRepository).save(any(LogEntity.class));

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () -> logMySql.salvar(logMessage));

        assertEquals("Falha ao salvar log no banco", exception.getMessage());
        verify(logRepository, times(1)).save(any(LogEntity.class));
    }

    @Test
    void testSalvarLogCriadoCorretamente() {
        // Arrange
        String logMessage = "Teste de log";

        // Act
        logMySql.salvar(logMessage);

        // Assert
        verify(logRepository, times(1)).save(argThat(logEntity -> 
            logEntity.getLog().equals(logMessage) && 
            logEntity.getData() != null &&
            logEntity.getData().isBefore(LocalDateTime.now().plusSeconds(1))
        ));
    }
}
