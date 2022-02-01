package com.academia.library.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String ROLE_NOT_FOUND_MESSAGE_ERROR = "Role with name=[%s] don't exist";

    public RoleNotFoundException(String name) {
        super(String.format(ROLE_NOT_FOUND_MESSAGE_ERROR, name));
    }
}

