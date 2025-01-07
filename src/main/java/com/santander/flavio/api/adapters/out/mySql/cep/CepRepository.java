package com.santander.flavio.api.adapters.out.mySql.cep;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CepRepository extends JpaRepository<CepEntity, Long>{

    CepEntity findByNumero(Long numero);
}
