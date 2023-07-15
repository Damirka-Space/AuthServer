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

//    @PostConstruct
//    public void init() {
//        var r = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientName("music-server")
//                .clientId("server")
//                .clientSecret(DefaultSecurityConfig.passwordEncoder().encode("secret"))
//                .redirectUri("http://localhost:4200/user/authorized")
//                .scope("profile")
//                .scope("music-server.read")
//                .scope("music-server.write")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
//                        .accessTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
//                        .refreshTokenTimeToLive(Duration.of(2, ChronoUnit.DAYS))
//                        .reuseRefreshTokens(false)
//                        .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
//                        .build())
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(false)
//                        .build()).build();
//
//        save(r);
//    }


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
