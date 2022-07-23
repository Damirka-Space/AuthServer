package com.damirka.authserver.exceptions.user;

public class UserException extends Exception {
    protected UserExceptionId id;

    public UserException(UserExceptionId id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id.ordinal();
    }

}
