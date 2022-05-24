package com.academia.library.controller;

import com.academia.library.dto.request.AuthorRequest;
import com.academia.library.dto.response.AuthorResponse;
import com.academia.library.service.AuthorService;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Author")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Get authors", description = "Get all authors")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public List<AuthorResponse> getAll(){
        return authorService.findAll();
    }

    @Operation(summary = "Get author", description = "Get author by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("{id}")
    public AuthorResponse fetById(@PathVariable Long id){
        return authorService.findById(id);
    }

    @Operation(summary = "Create author", description = "Create new author")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PostMapping
    public AuthorResponse create(@RequestBody AuthorRequest authorRequest) {
        return authorService.create(authorRequest);
    }

    @Operation(summary = "Update author", description = "Update author by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PutMapping("{id}")
    public AuthorResponse update(@PathVariable Long id, @RequestBody AuthorRequest authorRequest) {
        return authorService.update(id, authorRequest);
    }

    @Operation(summary = "Delete author", description = "Delete author by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
