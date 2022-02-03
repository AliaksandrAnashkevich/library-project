package com.academia.library.exception.handler;

import com.academia.library.exception.OrderNotFoundException;
import com.academia.library.exception.OrderStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity handleBookNotFound(OrderNotFoundException e) {
        log.error("Not found: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity handleOrderStatus(OrderStatusException e) {
        log.error("Conflict: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
