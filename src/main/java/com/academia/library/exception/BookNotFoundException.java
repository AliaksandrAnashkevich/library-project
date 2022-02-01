package com.academia.library.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String BOOK_NOT_FOUND_MESSAGE_ERROR = "Book with id=[%s] don't exist";

    public BookNotFoundException(Long id) {
        super(String.format(BOOK_NOT_FOUND_MESSAGE_ERROR, id));
    }
}