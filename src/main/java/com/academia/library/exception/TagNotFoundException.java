package com.academia.library.exception;

public class TagNotFoundException extends RuntimeException{

    private static final String TAG_NOT_FOUND_MESSAGE_ERROR = "Tag with id=[%s] don't exist";

    public TagNotFoundException(Long id) {
        super(String.format(TAG_NOT_FOUND_MESSAGE_ERROR, id));
    }
}
