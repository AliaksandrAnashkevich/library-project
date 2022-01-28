package com.academia.library.service;

import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;

import java.util.List;

public interface TagService {

    TagResponseDto findById(Long id);

    List<TagResponseDto> findAll();

    TagResponseDto create(TagRequestDto tagRequestDto);

    TagResponseDto update(Long id, TagRequestDto tagRequestDto);

    void delete(Long id);
}
