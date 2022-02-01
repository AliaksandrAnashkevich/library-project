package com.academia.library.exception;

public class InvalidAuthRequestDataException extends RuntimeException {

    private static final String INVALID_AUTH_MESSAGE_ERROR = "Invalid email or password.";

    public InvalidAuthRequestDataException() {
        super(INVALID_AUTH_MESSAGE_ERROR);
    }
}
