package com.academia.library.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String MESSAGE_ERROR = "Role with name=[%s] don't exist";

    public RoleNotFoundException(String name) {
        super(String.format(MESSAGE_ERROR, name));
    }
}

