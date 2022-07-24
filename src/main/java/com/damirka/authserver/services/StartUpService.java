package com.damirka.authserver.services;

import com.damirka.authserver.entities.RoleEntity;
import com.damirka.authserver.entities.RoleEnum;
import com.damirka.authserver.repositories.RoleRepository;
import com.damirka.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
class StartUpService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private StartUpService(UserRepository userRepository, RoleRepository roleRepository) {
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
    }
}
