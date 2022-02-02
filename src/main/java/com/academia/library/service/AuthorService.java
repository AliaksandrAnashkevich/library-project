package com.academia.library.service;

import com.academia.library.dto.AuthorRequest;
import com.academia.library.dto.AuthorResponse;
import com.academia.library.model.Author;

import java.util.List;

public interface AuthorService {

    AuthorResponse findById(Long id);

    List<AuthorResponse> findAll();

    AuthorResponse create(AuthorRequest authorRequest);

    AuthorResponse update(Long id, AuthorRequest authorRequest);

    void delete(Long id);
}
