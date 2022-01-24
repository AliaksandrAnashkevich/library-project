package com.academia.library.exeption;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE_CONSTRUCTION = "User with username=[%s] don't found";

    public UserNotFoundException(String email) {
        super(String.format(MESSAGE_CONSTRUCTION, email));
    }
}