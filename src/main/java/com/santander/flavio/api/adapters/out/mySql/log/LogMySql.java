package com.santander.flavio.api.adapters.out.mySql.log;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.santander.flavio.api.adapters.exceptions.DatabaseException;
import com.santander.flavio.api.application.ports.out.LogOutPort;

@Repository
public class LogMySql implements LogOutPort {

    private static final Logger logger = LoggerFactory.getLogger(LogMySql.class);

    private final LogRepository logRepository;

    public LogMySql(LogRepository logRepository) {
        logger.debug("Construtor");
        this.logRepository = logRepository;
    }

    public void salvar (String log) {
        logger.debug("Funcao salvar, parametros: log={}", log);
        try {
            LogEntity logEntity = new LogEntity(log, LocalDateTime.now());
            logRepository.save(logEntity);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Falha ao salvar log no banco", ex);
        }
    }    
}
