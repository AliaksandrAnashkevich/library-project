package com.academia.library.exception;

public class JwtTokenException extends RuntimeException{

    private static final String MESSAGE_ERROR = "Expired or invalid JWT token";

    public JwtTokenException() {
        super(MESSAGE_ERROR);
    }
}
