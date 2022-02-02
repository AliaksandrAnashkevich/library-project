package com.academia.library.exception;

public class TagAlreadyExistException extends RuntimeException {

    private static final String TAG_ALREADY_EXIST_MESSAGE_ERROR = "Tag with name=[%s] already exist";

    public TagAlreadyExistException(String name) {
        super(String.format(TAG_ALREADY_EXIST_MESSAGE_ERROR, name));
    }
}
