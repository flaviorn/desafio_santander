package com.santander.flavio.api.application.ports.out;

import com.santander.flavio.api.application.domain.Cep;

public interface CepOutPort {
    public Cep obter(Long cep);
    public void salvar(Cep cep);
}
