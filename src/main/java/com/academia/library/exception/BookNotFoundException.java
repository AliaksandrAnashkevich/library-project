package com.academia.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE_CONSTRUCTOR = "object with id=[%s] don't exist";

    public BookNotFoundException(Long id) {
        super(String.format(MESSAGE_CONSTRUCTOR, id));
    }
}
