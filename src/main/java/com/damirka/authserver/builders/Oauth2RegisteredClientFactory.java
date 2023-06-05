package com.damirka.authserver.builders;

import com.damirka.authserver.entities.Oauth2RegisteredClient;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Oauth2RegisteredClientFactory {

    public static RegisteredClient from(Oauth2RegisteredClient oauth2RegisteredClient) {
        var registeredClientBuilder = RegisteredClient.withId(oauth2RegisteredClient.getId())
                .clientId(oauth2RegisteredClient.getClientId())
                .clientIdIssuedAt(oauth2RegisteredClient.getClientIdIssuedAt())
                .clientSecret(oauth2RegisteredClient.getClientSecret())
                .clientSecretExpiresAt(oauth2RegisteredClient.getClientSecretExpiresAt())
                .clientName(oauth2RegisteredClient.getClientName())

                .clientAuthenticationMethods(a -> a.addAll(Arrays.stream(oauth2RegisteredClient.getClientAuthenticationMethods()
                        .split(",")).map(ClientAuthenticationMethod::new).collect(Collectors.toSet())))

                .authorizationGrantTypes(a -> a.addAll(Arrays.stream(oauth2RegisteredClient.getAuthorizationGrantTypes()
                        .split(",")).map(AuthorizationGrantType::new).collect(Collectors.toSet())))

                .redirectUris(r -> r.addAll(Arrays.stream(oauth2RegisteredClient.getRedirectUris().split(",")).collect(Collectors.toSet())))

                .scopes(s -> s.addAll(Arrays.stream(oauth2RegisteredClient.getScopes().split(",")).collect(Collectors.toSet())))

                .clientSettings(oauth2RegisteredClient.getClientSettings())
                .tokenSettings(oauth2RegisteredClient.getTokenSettings());

        return registeredClientBuilder.build();
    }

    public static Oauth2RegisteredClient to(RegisteredClient registeredClient) {
        Oauth2RegisteredClient oauth2RegisteredClient = new Oauth2RegisteredClient();
        oauth2RegisteredClient.setId(registeredClient.getId());
        oauth2RegisteredClient.setClientId(registeredClient.getClientId());
        oauth2RegisteredClient.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        oauth2RegisteredClient.setClientSecret(registeredClient.getClientSecret());
        oauth2RegisteredClient.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        oauth2RegisteredClient.setClientName(registeredClient.getClientName());
        oauth2RegisteredClient.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.joining(",")));
        oauth2RegisteredClient.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.joining(",")));
        oauth2RegisteredClient.setRedirectUris(String.join(",", registeredClient.getRedirectUris()));
        oauth2RegisteredClient.setScopes(String.join(",", registeredClient.getScopes()));
        oauth2RegisteredClient.setClientSettings(registeredClient.getClientSettings());
        oauth2RegisteredClient.setTokenSettings(registeredClient.getTokenSettings());

        return  oauth2RegisteredClient;
    }
}
