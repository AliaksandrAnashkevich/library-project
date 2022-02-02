package com.academia.library.controller;

import com.academia.library.dto.BookRequest;
import com.academia.library.dto.BookResponse;
import com.academia.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.findAll();
    }

    @Operation(summary = "Get book", description = "Get book by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("{id}")
    public BookResponse getBookById(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Create book", description = "Create new book")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PostMapping
    public BookResponse create(@RequestBody BookRequest bookRequest) {
        return bookService.create(bookRequest);
    }

    @Operation(summary = "Update book", description = "Update book by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PutMapping("{id}")
    public BookResponse update(@PathVariable("id") Long id, @RequestBody BookRequest bookRequest) {
        return bookService.update(id, bookRequest);
    }

    @Operation(summary = "Delete book", description = "Delete book by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
