package com.academia.library.exception;

public class OrderStatusException extends RuntimeException {

    private static final String CREATE_ORDER_STATUS_MESSAGE_ERROR = "Cann't create order with status [%s]";

    public OrderStatusException(String status) {
        super(String.format(CREATE_ORDER_STATUS_MESSAGE_ERROR, status));
    }
}
