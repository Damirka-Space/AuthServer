package com.damirka.authserver.builders;

import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.entities.UserEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public abstract class UserFactory {

    public static UserEntity userFromUserDto(UserRegistrationDto user) throws ParseException {

        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setGender(user.getGender());
        newUser.setPhone(user.getPhone());

        Date birthday = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(user.getBirthday());
        newUser.setDateOfBirth(birthday);

        Date created = new Date();
        newUser.setCreated(created);
        newUser.setUpdated(created);

        newUser.setBlocked(false);
        newUser.setDeleted(false);

        newUser.setActivated(true);

        return newUser;
    }
}
