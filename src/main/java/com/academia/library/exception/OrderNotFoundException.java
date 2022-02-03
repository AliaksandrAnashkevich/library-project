package com.academia.library.exception;

public class OrderNotFoundException extends RuntimeException{

    private static final String ORDER_NOT_FOUND_MESSAGE_ERROR = "Order with id=[%s] don't exist";

    public OrderNotFoundException(Long id) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE_ERROR, id));
    }
}
