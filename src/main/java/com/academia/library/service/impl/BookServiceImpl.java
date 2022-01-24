package com.academia.library.service.impl;

import com.academia.library.dto.BookResponseDto;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.mapper.BookMapper;
import com.academia.library.model.Book;
import com.academia.library.repository.BookRepository;
import com.academia.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        log.info("IN findById - book found by id: {}", id);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        List<Book> books = bookRepository.findAll();
        log.info("IN getAll - {} users found", books.size());
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
