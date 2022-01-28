package com.academia.library.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static final String MESSAGE_ERROR = "Author with id=[%s] don't exist";

    public AuthorNotFoundException(Long id) {
        super(String.format(MESSAGE_ERROR, id));
    }
}
