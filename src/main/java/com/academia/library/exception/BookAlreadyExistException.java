package com.academia.library.exception;

public class BookAlreadyExistException extends RuntimeException {

    private static final String MESSAGE_ERROR = "Book already exist";

    public BookAlreadyExistException() {
        super(MESSAGE_ERROR);
    }
}
