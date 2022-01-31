package com.academia.library.controller;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.AuthorResponseDto;
import com.academia.library.service.AuthorService;
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
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorResponseDto> getAll(){
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public AuthorResponseDto fetById(@PathVariable Long id){
        return authorService.findById(id);
    }

    @PostMapping
    public AuthorResponseDto create(@RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.create(authorRequestDto);
    }

    @PutMapping("{id}")
    public AuthorResponseDto update(@PathVariable Long id, @RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.update(id, authorRequestDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
