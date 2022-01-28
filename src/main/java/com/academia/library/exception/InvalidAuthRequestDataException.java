package com.academia.library.exception;

public class InvalidAuthRequestDataException extends RuntimeException {

    private static final String MESSAGE_CONSTRUCTION = "Invalid email or password.";

    public InvalidAuthRequestDataException() {
        super(MESSAGE_CONSTRUCTION);
    }
}
