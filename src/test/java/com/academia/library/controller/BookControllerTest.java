package com.academia.library.controller;

import com.academia.library.dto.BookResponse;
import com.academia.library.mapper.AuthorMapper;
import com.academia.library.mapper.BookMapper;
import com.academia.library.mapper.TagMapper;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.model.Tag;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import com.academia.library.util.TestDataCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookMapper bookMapper ;

    @Autowired
    private TagMapper tagMapper ;

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
    void getBookById() throws Exception {
        // given
        String url = "/books/{id}";
        // when
        String response = mockMvc.perform(get(url, actual.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        BookResponse extend = objectMapper.readValue(response, BookResponse.class);
        assertEquals(actual.getPrice(), extend.getPrice());
        assertEquals(actual.getTitle(), extend.getTitle());
    }

    @Test
    void getAllBooks() throws Exception {
        // given
        String url = "/books";
        // when
        String response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        List<BookResponse> list = Arrays.asList(objectMapper.readValue(response, BookResponse[].class));
        assertTrue(list.size() > 0);
    }
}