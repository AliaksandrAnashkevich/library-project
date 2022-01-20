package com.academia.library.controller;

import com.academia.library.dto.BookDto;
import com.academia.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks(){
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public BookDto getBookById(@PathVariable("id") Long id){
        return bookService.findById(id);
    }
}
