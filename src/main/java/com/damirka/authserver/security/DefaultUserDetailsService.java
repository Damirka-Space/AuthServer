package com.damirka.authserver.security;

import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.repositories.UserRepository;
import com.damirka.authserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public DefaultUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DefaultUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getUserByUsername(username);

        if(Objects.isNull(user))
            throw new UsernameNotFoundException("User " + username + " not found!");

        return DefaultUserFactory.create(user);
    }
}
