package com.damirka.authserver.services;

import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.entities.GenderEnum;
import com.damirka.authserver.entities.RoleEntity;
import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.exceptions.user.UserException;
import com.damirka.authserver.repositories.RoleRepository;
import com.damirka.authserver.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Objects;

@Service
class StartUpService {

    @Value("${admin.login}")
    private String adminLogin;
    @Value("${admin.password}")
    private String adminPassword;

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public StartUpService(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void startUp() {
        // Check all roles
        for(RoleEnum role : RoleEnum.values()) {
            if (Objects.isNull(roleRepository.findByName(role.name())))
            {
                RoleEntity newRole = new RoleEntity();
                newRole.setName(role.name());
                roleRepository.save(newRole);
            }
        }

        UserRegistrationDto newUser = new UserRegistrationDto();
        newUser.setUsername(adminLogin);
        newUser.setPassword(adminPassword);
        newUser.setGender(GenderEnum.Male.toString());
        try {
            userService.registerUser(newUser);
            userService.setAdmin(newUser.getUsername());
        } catch (UserException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
