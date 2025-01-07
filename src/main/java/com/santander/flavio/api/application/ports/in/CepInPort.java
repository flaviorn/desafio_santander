package com.santander.flavio.api.application.ports.in;

import com.santander.flavio.api.application.domain.Cep;

public interface CepInPort {
    public Cep consultar(Long cep);    
}
