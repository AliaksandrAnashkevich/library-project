package com.academia.library.exeption.handler;

import com.academia.library.exeption.InvalidAuthRequestDataException;
import com.academia.library.exeption.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidAuthRequestDataException.class)
    public ResponseEntity handleInvalidAuthRequestData(IllegalArgumentException e){
        log.error("Unauthorized {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException e){
        log.error("Unauthorized {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
