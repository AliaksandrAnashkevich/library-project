package com.academia.library.service.impl;

import com.academia.library.mapper.AuthorMapper;
import com.academia.library.mapper.BookMapper;
import com.academia.library.mapper.TagMapper;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.model.Tag;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import com.academia.library.service.BookService;
import com.academia.library.util.TestDataCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TagMapper tagMapper;

    private Book actual;

    @BeforeEach
    private void insertTestData() {
        Tag tag = tagMapper.toEntity(TestDataCreator.TEST_TAG);
        Author author = authorMapper.toEntity(TestDataCreator.TEST_AUTHOR);
        Book book = bookMapper.toEntity(TestDataCreator.TEST_BOOK);

        tagRepository.save(tag);
        authorRepository.save(author);
        book.setAuthor(author);
        book.setTags(Set.of(tag));
        bookRepository.save(book);
        actual = book;
    }

    @AfterEach
    private void deleteAll() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    void findById() {
        // given
        var actualDto = TestDataCreator.TEST_BOOK;
        // when
        var extend = bookService.findById(actual.getId());
        // then
        assertEquals(actualDto.getTitle(), extend.getTitle());
        assertEquals(actualDto.getPrice(), extend.getPrice());
    }

    @Test
    void findAll() {
        // when
        var extendList = bookService.findAll();
        // then
        assertTrue(extendList.size() > 0);
    }
}