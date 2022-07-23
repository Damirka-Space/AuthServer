package com.damirka.authserver.exceptions.user;

public class UserAlreadyExistException extends UserException {

    public UserAlreadyExistException() {
        super(UserExceptionId.USER_WITH_THIS_USERNAME_ALREADY_EXIST, "User with this username already exist");
    }

}
