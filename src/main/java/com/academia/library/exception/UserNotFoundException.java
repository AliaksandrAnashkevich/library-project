package com.academia.library.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND_MESSAGE_ERROR = "User with username=[%s] don't found";

    public UserNotFoundException(String email) {
        super(String.format(USER_NOT_FOUND_MESSAGE_ERROR, email));
    }
}