package com.academia.library.controller;

import com.academia.library.dto.BookResponse;
import com.academia.library.dto.TagRequest;
import com.academia.library.dto.TagResponse;
import com.academia.library.service.TagService;
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
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "Tag")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Get tags", description = "Get all tags")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public List<TagResponse> getAll() {
        return tagService.findAll();
    }

    @Operation(summary = "Get tag", description = "Get tag by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("{id}")
    public TagResponse getById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @Operation(summary = "Create tag", description = "Create new tag")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PostMapping
    public TagResponse create(@RequestBody TagRequest tagRequestDto) {
        return tagService.create(tagRequestDto);
    }

    @Operation(summary = "Update tag", description = "Update tag by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    @PutMapping("{id}")
    public TagResponse update(@PathVariable Long id, @RequestBody TagRequest tagRequestDto) {
        return tagService.update(id, tagRequestDto);
    }

    @Operation(summary = "Delete tag", description = "Delete tag by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
