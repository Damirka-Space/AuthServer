package com.damirka.authserver.controllers;

import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class OAuth2Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userinfo")
    public Map<String, Object> user(Principal principal) {

        if(Objects.nonNull(principal)) {
            UserEntity user = userRepository.findByUsername((principal.getName()));
            if(Objects.nonNull(user)) {

                Map<String, Object> map = new HashMap<>();
                map.put("sub", principal.getName());
                map.put("username", user.getUsername());
                map.put("created", user.getCreated());
                map.put("email", user.getEmail());
                map.put("gender", user.getGender());

                return map;
            }
        }

        return Collections.singletonMap("Error", "User not found!");
    }
}
