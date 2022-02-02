package com.academia.library.exception;

public class BookAlreadyExistException extends RuntimeException {

    private static final String BOOK_ALREADY_EXIST_MESSAGE_ERROR = "Book already exist";

    public BookAlreadyExistException() {
        super(BOOK_ALREADY_EXIST_MESSAGE_ERROR);
    }
}
