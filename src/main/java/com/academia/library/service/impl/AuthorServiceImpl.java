package com.academia.library.service.impl;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.AuthorResponseDto;
import com.academia.library.exception.AuthorAlreadyExistException;
import com.academia.library.exception.AuthorNotFoundException;
import com.academia.library.mapper.AuthorMapper;
import com.academia.library.model.Author;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDto findById(Long id) {
        Author author = getAuthorOrThrowException(id);
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        if (authorRepository.existsByFirstNameAndLastName(authorRequestDto.getFirstName(), authorRequestDto.getLastName())) {
            throw new AuthorAlreadyExistException(authorRequestDto.getFirstName(), authorRequestDto.getLastName());
        }

        Author author = authorMapper.toEntity(authorRequestDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    public AuthorResponseDto update(Long id, AuthorRequestDto authorRequestDto) {
        if (authorRepository.existsByFirstNameAndLastName(authorRequestDto.getFirstName(), authorRequestDto.getLastName())) {
            throw new AuthorAlreadyExistException(authorRequestDto.getFirstName(), authorRequestDto.getLastName());
        }

        Author author = getAuthorOrThrowException(id);
        author = authorMapper.updateRequestToEntity(authorRequestDto, author);
        Author updateAuthor = authorRepository.save(author);
        return authorMapper.toDto(updateAuthor);
    }

    @Override
    public void delete(Long id) {
        Author author = getAuthorOrThrowException(id);
        authorRepository.delete(author);
    }

    public Author getAuthorOrThrowException(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
