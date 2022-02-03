package com.academia.library.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static final String AUTHOR_NOT_FOUND_MESSAGE_ERROR = "Author with id=[%s] don't exist";

    public AuthorNotFoundException(Long id) {
        super(String.format(AUTHOR_NOT_FOUND_MESSAGE_ERROR, id));
    }
}
