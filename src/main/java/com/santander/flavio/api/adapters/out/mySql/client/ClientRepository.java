package com.santander.flavio.api.adapters.out.mySql.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

    ClientEntity findByClientId(String clientId);
}
