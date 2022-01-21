package com.academia.library.exception.handler;

import com.academia.library.dto.ExceptionDto;
import com.academia.library.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleBookNotFound(BookNotFoundException e) {
        ExceptionDto message = ExceptionDto.builder()
                .codeStatus(HttpStatus.NOT_FOUND.value())
                .messageStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .messageException(e.getMessage())
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
