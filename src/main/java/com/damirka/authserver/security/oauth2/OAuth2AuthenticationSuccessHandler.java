package com.damirka.authserver.security.oauth2;

import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.exceptions.user.UserException;
import com.damirka.authserver.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;
import java.util.Random;

@Service
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    OAuth2AuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserRegistrationDto newUser = new UserRegistrationDto();

        if(authentication.getPrincipal() instanceof DefaultOAuth2User oauthUser) {

            if(Objects.nonNull(userService.getUserByUsername(oauthUser.getName()))) {
                super.onAuthenticationSuccess(request, response, authentication);
                return;
            }

            newUser.setUsername(oauthUser.getName());
            newUser.setEmail(oauthUser.getEmail());
            newUser.setGender(oauthUser.getGender());
        } else {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            if(Objects.nonNull(userService.getUserByUsername(oidcUser.getSubject()))) {
                super.onAuthenticationSuccess(request, response, authentication);
                return;
            }

            newUser.setUsername(oidcUser.getName());
            newUser.setEmail(oidcUser.getEmail());
            newUser.setGender(oidcUser.getGender());
        }

        newUser.setPassword(alphaNumericString(30));

        try {
            userService.registerUser(newUser);
        } catch (UserException | ParseException e) {
            throw new RuntimeException(e);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
