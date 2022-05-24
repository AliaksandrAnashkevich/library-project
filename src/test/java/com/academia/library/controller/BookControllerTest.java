package com.academia.library.controller;

import com.academia.library.dto.request.BookRequest;
import com.academia.library.dto.response.BookResponse;
import com.academia.library.mapper.AuthorMapper;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private TagMapper tagMapper;

    private Book actual;

    @BeforeEach
    private void insertTestData() {
        Tag tag = tagMapper.toEntity(TestDataCreator.TEST_TAG);
        tagRepository.save(tag);

        Author author = authorMapper.toEntity(TestDataCreator.TEST_AUTHOR);
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle(TestDataCreator.TEST_BOOK_TITLE);
        book.setPrice(new BigDecimal("10.00"));
        book.setAuthor(author);
        book.setTags(Set.of(tag));
        book.setCreateAt(LocalDateTime.now());
        book.setUpdateAt(LocalDateTime.now());
        bookRepository.save(book);
        actual = book;
    }

    @AfterEach
    private void deleteAll() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @DisplayName("Get book by id")
    @Test
    void getBookById() throws Exception {
        // given
        var url = "/books/{id}";
        // when
        var response = mockMvc.perform(get(url, actual.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, BookResponse.class);
        assertThat(actual.getPrice()).isEqualTo(extend.getPrice());
        assertThat(actual.getTitle()).isEqualTo(extend.getTitle());
    }

    @DisplayName("Get all books")
    @Test
    void getAllBooks() throws Exception {
        // given
        var url = "/books";
        // when
        var response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = Arrays.asList(objectMapper.readValue(response, BookResponse[].class));
        assertTrue(extend.size() > 0);
    }

    @DisplayName("Create new book")
    @Test
    @WithMockUser(username = "test@example.com", roles = "ADMIN", password = "Aa12356")
    void create() throws Exception {
        // given
        var actualDto = BookRequest.builder()
                .title("Testing book")
                .price(new BigDecimal("5.00"))
                .authorId(actual.getAuthor().getId())
                .tagsId(actual.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList()))
                .build();
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, BookResponse.class);
        assertThat(actualDto.getTitle()).isEqualTo(extend.getTitle());
        assertThat(actualDto.getPrice()).isEqualTo(extend.getPrice());
    }

    @DisplayName("Update book by id")
    @Test
    @WithMockUser(username = "test@example.com", roles = "ADMIN", password = "Aa12356")
    void update() throws Exception {
        // given
        var bookRequestDto = BookRequest.builder()
                .title(actual.getTitle())
                .price(new BigDecimal("5.00"))
                .authorId(actual.getAuthor().getId())
                .tagsId(actual.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList()))
                .build();
        var inputJson = objectMapper.writeValueAsString(bookRequestDto);
        var request = put("/books/{id}", actual.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, BookResponse.class);
        assertThat(actual.getTitle()).isEqualTo(extend.getTitle());
        assertThat(new BigDecimal("5.00")).isEqualTo(extend.getPrice());
        assertThat(actual.getAuthor().getFirstName()).isEqualTo(extend.getAuthor().getFirstName());
        assertThat(actual.getAuthor().getLastName()).isEqualTo(extend.getAuthor().getLastName());
    }

    @DisplayName("Delete book by id")
    @Test
    @WithMockUser(username = "test@example.com", roles = "ADMIN", password = "Aa12356")
    void remove() throws Exception {
        // given
        var url = "/books/{id}";
        // when, then
        this.mockMvc.perform(delete(url, actual.getId()))
                .andExpect(status()
                        .isOk());
    }
}