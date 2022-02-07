package com.academia.library.exception.handler;

import com.academia.library.exception.BookAlreadyExistException;
import com.academia.library.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity handleBookNotFound(BookNotFoundException e) {
        log.error("Bad request: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity handleBookNotFound(BookAlreadyExistException e) {
        log.error("Conflict: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
