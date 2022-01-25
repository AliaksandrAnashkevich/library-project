package com.academia.library.controller;

import com.academia.library.dto.BookResponseDto;
import com.academia.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get books", description = "Get all books")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public List<BookResponseDto> getAllBooks(){
        return bookService.findAll();
    }

    @Operation(summary = "Get cities", description = "Get book by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("{id}")
    public BookResponseDto getBookById(@PathVariable("id") Long id){
        return bookService.findById(id);
    }
}
