package com.academia.library.service.impl;

import com.academia.library.dto.request.AuthorRequest;
import com.academia.library.dto.response.AuthorResponse;
import com.academia.library.exception.AuthorNotFoundException;
import com.academia.library.mapper.AuthorMapper;
import com.academia.library.model.Author;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.service.AuthorService;
import com.academia.library.validator.AuthorValidator;
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
    private final AuthorValidator authorValidator;

    @Override
    @Transactional(readOnly = true)
    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorResponse create(AuthorRequest authorRequest) {
        authorValidator.validate(authorRequest);

        Author author = authorMapper.toEntity(authorRequest);
        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDto(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorResponse update(Long id, AuthorRequest authorRequest) {
        authorValidator.validate(authorRequest);

        Author author = authorRepository.findById(id)
                .map(auth -> authorMapper.updateRequestToEntity(authorRequest, auth))
                .orElseThrow(() -> new AuthorNotFoundException(id));

        Author updatedAuthor = authorRepository.save(author);

        return authorMapper.toDto(updatedAuthor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        authorRepository.delete(author);
    }
}
