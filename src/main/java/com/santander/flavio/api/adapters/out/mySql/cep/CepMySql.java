package com.santander.flavio.api.adapters.out.mySql.cep;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.santander.flavio.api.adapters.exceptions.DatabaseException;
import com.santander.flavio.api.application.domain.Cep;
import com.santander.flavio.api.application.ports.out.CepOutPort;

@Repository
public class CepMySql implements CepOutPort {

    private static final Logger logger = LoggerFactory.getLogger(CepMySql.class);

    private final CepRepository cepRepository;
    private final ModelMapper modelMapper;

    public CepMySql(CepRepository cepRepository, ModelMapper modelMapper) {
        logger.debug("Construtor");
        this.cepRepository = cepRepository;
        this.modelMapper = modelMapper;
    }

    public Cep obter (Long cep) {
        logger.debug("Funcao obter, parametros: cep={}", cep);
        try {
            CepEntity cepEntity = cepRepository.findByNumero(cep);
            return (cepEntity != null) ? modelMapper.map(cepEntity, Cep.class) : null;
        } catch (DataAccessException ex) {
            throw new DatabaseException("Falha ao obter cep no banco", ex);
        }
    }

    public void salvar (Cep cep) {
        logger.debug("Funcao salvar, parametros: cep={}", cep);
        try {
            cepRepository.save(modelMapper.map(cep, CepEntity.class));
        } catch (DataAccessException ex) {
            throw new DatabaseException("Falha ao salvar cep no banco", ex);
        }
    }    
}
