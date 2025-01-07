package com.santander.flavio.api.application.ports.out;

import com.santander.flavio.api.application.domain.Cep;

public interface ViaCepOutPort {
    public Cep obter(Long cep);
}
