package com.damirka.authserver.services;

import com.damirka.authserver.builders.Oauth2RegisteredClientFactory;
import com.damirka.authserver.repositories.Oauth2RegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class Oauth2RegisteredClientService implements RegisteredClientRepository {

    private Oauth2RegisteredClientRepository oauth2RegisteredClientRepository;

    @Autowired
    public Oauth2RegisteredClientService(Oauth2RegisteredClientRepository oauth2RegisteredClientRepository) {
        this.oauth2RegisteredClientRepository = oauth2RegisteredClientRepository;
    }


    @Override
    public void save(RegisteredClient registeredClient) {
        oauth2RegisteredClientRepository.save(Oauth2RegisteredClientFactory.to(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        var opValue = oauth2RegisteredClientRepository.findById(id);

        if(opValue.isEmpty())
            return null;

        return Oauth2RegisteredClientFactory.from(opValue.get());
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var opValue = oauth2RegisteredClientRepository.findByClientId(clientId);

        if(opValue.isEmpty())
            return null;

        return Oauth2RegisteredClientFactory.from(opValue.get());
    }
}
