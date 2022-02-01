package com.academia.library.exception;

public class JwtTokenException extends RuntimeException{

    private static final String INVALID_JWT_MESSAGE_ERROR = "Expired or invalid JWT token";

    public JwtTokenException() {
        super(INVALID_JWT_MESSAGE_ERROR);
    }
}