package com.santander.flavio.api.adapters.out.mySql.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long>{

}
