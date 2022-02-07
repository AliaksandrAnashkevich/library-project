package com.academia.library.exception.handler;

import com.academia.library.exception.TagAlreadyExistException;
import com.academia.library.exception.TagNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TagExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity handleBookNotFound(TagNotFoundException e) {
        log.error("Not Found: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity handleBookNotFound(TagAlreadyExistException e) {
        log.error("Conflict: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
