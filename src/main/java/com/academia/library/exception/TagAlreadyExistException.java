package com.academia.library.exception;

public class TagAlreadyExistException extends RuntimeException{

    private static final String MESSAGE_ERROR = "Tag with name=[%s] already exist";

    public TagAlreadyExistException(String name) {
        super(String.format(MESSAGE_ERROR, name));
    }
}
