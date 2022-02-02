package com.academia.library.exception;

public class AuthorAlreadyExistException extends RuntimeException{

    private static final String AUTHOR_ALREADY_EXIST_MESSAGE_ERROR = "Author with name=[%s %s] already exist";

    public AuthorAlreadyExistException(String firstName, String lastName) {
        super(String.format(AUTHOR_ALREADY_EXIST_MESSAGE_ERROR, firstName, lastName));
    }
}
