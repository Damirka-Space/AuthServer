package com.damirka.authserver.services;

import com.damirka.authserver.builders.UserFactory;
import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.repositories.RoleRepository;
import com.damirka.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(UserRegistrationDto user) throws Exception {

        UserEntity newUser = getUserByUsername(user.getUsername());

        if(Objects.nonNull(newUser))
            throw new Exception("User with this username already registered");

        newUser = UserFactory.userFromUserDto(user);

        newUser.setRoles(Collections.singletonList(roleRepository.findByName(RoleEnum.USER.toString())));

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser.setPhone(passwordEncoder.encode(newUser.getPhone()));

        userRepository.save(newUser);
    }

}
