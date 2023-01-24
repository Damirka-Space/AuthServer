package com.damirka.authserver.security.oauth2;

import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OidcUserInfoService {

    @Autowired
    private UserRepository userRepository;

    public OidcUserInfo loadUser(String username) {
        UserEntity user = userRepository.findByUsername(username);

        String subject = username;
        String email = user.getEmail();
        String gender = user.getGender();

        String birthday = null;
        if(Objects.nonNull(user.getDateOfBirth()))
            birthday = user.getDateOfBirth().toString();

        String phoneNumber = user.getPhone();

        return OidcUserInfo.builder()
                .subject(subject)
                .email(email)
                .gender(gender)
                .birthdate(birthday)
                .phoneNumber(phoneNumber)
                .claim("roles", user.getRoles())
                .build();

    }
}
