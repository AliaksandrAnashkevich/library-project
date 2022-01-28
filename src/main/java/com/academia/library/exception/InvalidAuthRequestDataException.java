package com.academia.library.exception;

public class InvalidAuthRequestDataException extends RuntimeException {

    private static final String MESSAGE_ERROR = "Invalid email or password.";

    public InvalidAuthRequestDataException() {
        super(MESSAGE_ERROR);
    }
}
