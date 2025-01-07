package com.santander.flavio.api.application.ports.out;

import com.santander.flavio.api.application.domain.Client;

public interface ClientOutPort {
    public void autenticar(Client client);
}
