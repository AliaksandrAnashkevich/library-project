package com.academia.library.controller;

import com.academia.library.dto.BookDto;
import com.academia.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getAll(){
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable("id") Long id){
        return bookService.findById(id);
    }
}
