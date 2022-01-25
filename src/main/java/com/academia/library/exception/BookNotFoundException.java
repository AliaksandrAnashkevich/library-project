package com.academia.library.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE_CONSTRUCTOR = "Book with id=[%s] don't exist";

    public BookNotFoundException(Long id) {
        super(String.format(MESSAGE_CONSTRUCTOR, id));
    }
}
