package com.damirka.authserver.controllers;

import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> user(Principal principal) {

        if(Objects.nonNull(principal)) {
            UserEntity user = userService.getUserByUsername(principal.getName());
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }
}
