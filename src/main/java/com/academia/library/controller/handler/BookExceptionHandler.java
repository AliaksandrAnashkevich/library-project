package com.academia.library.controller.handler;

import com.academia.library.dto.ExceptionDto;
import com.academia.library.eception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ExceptionDto postNotFound(RuntimeException e) {
        return ExceptionDto.builder()
                .codeStatus(HttpStatus.NOT_FOUND.value())
                .messageStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .messageException(e.getMessage())
                .build();
    }
}
