package com.damirka.authserver.exceptions.user;

import java.util.Arrays;
import java.util.Locale;

public enum UserExceptionId {
    USER_WITH_THIS_USERNAME_ALREADY_EXIST,
    BIRTHDAY_NOT_ENTERED;


    @Override
    public String toString() {
        String name = name();
        name = name.toLowerCase(Locale.ROOT);
        name = name.replace("_", " ");
        return name;
    }
}
