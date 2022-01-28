package com.academia.library.exception;

public class JwtTokenException extends RuntimeException{

    private static final String MESSAGE_CONSTRUCTION = "Expired or invalid JWT token";

    public JwtTokenException() {
        super(MESSAGE_CONSTRUCTION);
    }
}
