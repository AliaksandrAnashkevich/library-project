package com.academia.library.service;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.AuthorResponseDto;
import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;

import java.util.List;

public interface AuthorService {

    AuthorResponseDto findById(Long id);

    List<AuthorResponseDto> findAll();

    AuthorResponseDto create(AuthorRequestDto authorRequestDto);

    AuthorResponseDto update(Long id, AuthorRequestDto authorRequestDto);

    void delete(Long id);
}
