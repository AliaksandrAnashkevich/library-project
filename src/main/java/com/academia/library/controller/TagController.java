package com.academia.library.controller;

import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;
import com.academia.library.service.TagService;
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
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagResponseDto> getAll() {
        return tagService.findAll();
    }

    @GetMapping("{id}")
    public TagResponseDto getById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public TagResponseDto create(@RequestBody TagRequestDto tagRequestDto) {
        return tagService.create(tagRequestDto);
    }

    @PutMapping("{id}")
    public TagResponseDto update(@PathVariable Long id, @RequestBody TagRequestDto tagRequestDto) {
        return tagService.update(id, tagRequestDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
