package com.santander.flavio.api.application.ports.in;

import com.santander.flavio.api.application.domain.Client;

public interface LoginInPort {
    public String logar(Client client);    
}
