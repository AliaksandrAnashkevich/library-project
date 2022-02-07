package com.academia.library.exception.handler;

import com.academia.library.exception.AuthorAlreadyExistException;
import com.academia.library.exception.AuthorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthorExceptionHandler {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity handleBookNotFound(AuthorNotFoundException e) {
        log.error("Not Found: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AuthorAlreadyExistException.class)
    public ResponseEntity handleBookNotFound(AuthorAlreadyExistException e) {
        log.error("Conflict: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
