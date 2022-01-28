package com.academia.library.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE_ERROR = "User with username=[%s] don't found";

    public UserNotFoundException(String email) {
        super(String.format(MESSAGE_ERROR, email));
    }
}