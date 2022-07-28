package com.damirka.authserver.services;

import com.damirka.authserver.builders.UserFactory;
import com.damirka.authserver.config.DefaultSecurityConfig;
import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.entities.RoleEntity;
import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.entities.UserEntity;
import com.damirka.authserver.exceptions.user.UserAlreadyExistException;
import com.damirka.authserver.exceptions.user.UserException;
import com.damirka.authserver.repositories.RoleRepository;
import com.damirka.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public  UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = DefaultSecurityConfig.passwordEncoder();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(UserRegistrationDto user) throws UserException, ParseException {

        UserEntity newUser = getUserByUsername(user.getUsername());

        if(Objects.nonNull(newUser))
            throw new UserAlreadyExistException();

        newUser = UserFactory.userFromUserDto(user);

        newUser.setRoles(Collections.singletonList(roleRepository.findByName(RoleEnum.USER.toString())));

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if(Objects.nonNull(newUser.getPhone()))
            newUser.setPhone(passwordEncoder.encode(newUser.getPhone()));

        userRepository.save(newUser);
    }

    public void setAdmin(String username) {
        UserEntity user = getUserByUsername(username);
        if(Objects.nonNull(user)) {
            List<RoleEntity> roles = user.getRoles();
            roles.add(roleRepository.findByName(RoleEnum.ADMIN.name()));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

}
