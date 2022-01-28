package com.academia.library.exception;

public class AuthorAlreadyExistException extends RuntimeException{

    private static final String MESSAGE_ERROR = "Author with name=[%s %s] already exist";

    public AuthorAlreadyExistException(String firstName, String lastName) {
        super(String.format(MESSAGE_ERROR, firstName, lastName));
    }
}
