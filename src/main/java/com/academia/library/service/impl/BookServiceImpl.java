package com.academia.library.service.impl;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.exception.BookAlreadyExistException;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.mapper.BookMapper;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.model.Tag;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import com.academia.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        Book book = getBookOrThrowException(id);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow();

        if (bookRepository.existsByTitleAndAuthor(bookRequestDto.getTitle(), author)) {
            throw new BookAlreadyExistException();
        }

        Set<Tag> tags = new HashSet<>();
        for (Long tagId : bookRequestDto.getTagsId()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow();
            tags.add(tag);
        }

        Book book = bookMapper.toEntity(bookRequestDto);
        book.setAuthor(author);
        book.setTags(tags);

        Book createBook = bookRepository.save(book);

        return bookMapper.toDto(createBook);
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto bookRequestDto) {
        Book book = getBookOrThrowException(id);

        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow();

        if (bookRepository.existsByTitleAndAuthor(bookRequestDto.getTitle(), author)) {
            throw new BookAlreadyExistException();
        }

        Set<Tag> tags = new HashSet<>();
        for (Long tagId : bookRequestDto.getTagsId()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow();
            tags.add(tag);
        }

        book = bookMapper.updateRequestToEntity(bookRequestDto, book);
        book.setAuthor(author);
        book.setTags(tags);

        Book createBook = bookRepository.save(book);

        return bookMapper.toDto(createBook);
    }

    @Override
    public void delete(Long id) {
        Book book = getBookOrThrowException(id);
        bookRepository.delete(book);
    }

    public Book getBookOrThrowException(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
