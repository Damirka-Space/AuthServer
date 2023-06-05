package com.damirka.authserver.repositories;

import com.damirka.authserver.entities.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2RegisteredClientRepository extends JpaRepository<Oauth2RegisteredClient, String> {

    Optional<Oauth2RegisteredClient> findByClientId(String clientId);
}
