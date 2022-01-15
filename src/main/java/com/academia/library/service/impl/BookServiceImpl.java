package com.academia.library.service.impl;

import com.academia.library.dto.BookDto;
import com.academia.library.mapper.BookMapper;
import com.academia.library.model.Book;
import com.academia.library.repository.BookRepository;
import com.academia.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow();
        return BookMapper.INSTANCE.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
