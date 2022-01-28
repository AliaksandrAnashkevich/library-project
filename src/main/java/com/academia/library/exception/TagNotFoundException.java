package com.academia.library.exception;

public class TagNotFoundException extends RuntimeException{

    private static final String MESSAGE_ERROR = "Tag with id=[%s] don't exist";

    public TagNotFoundException(Long id) {
        super(String.format(MESSAGE_ERROR, id));
    }
}
