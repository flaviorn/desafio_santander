package com.santander.flavio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.flavio.api.domain.Cep;

public interface CepRepository extends JpaRepository<Cep, Long>{

    Cep findByNumero(Long numero);
    
}
