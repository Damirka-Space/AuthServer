package com.damirka.authserver.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOidcUserService extends OidcUserService {
    public CustomOidcUserService() {
        super();
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        // TODO
        // 1) Fetch the authority information from the protected resource using accessToken


        // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

        oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser);

        return oidcUser;
    }
}
