package com.academia.library.exception;

public class OrderStatusNotFoundException extends RuntimeException {

    private static final String ORDER_STATUS_NOT_FOUND_MESSAGE_ERROR = "Order Status with value [%s] doesn't exist";

    public OrderStatusNotFoundException(String name) {
        super(String.format(ORDER_STATUS_NOT_FOUND_MESSAGE_ERROR, name));
    }
}
