package com.academia.library.controller;

import com.academia.library.dto.AuthorRequest;
import com.academia.library.dto.AuthorResponse;
import com.academia.library.mapper.AuthorMapper;
import com.academia.library.model.Author;
import com.academia.library.repository.AuthorRepository;
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

import java.util.Arrays;

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
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorMapper authorMapper;

    private Author actual;

    @BeforeEach
    private void insertTestData() {
        Author author = authorMapper.toEntity(TestDataCreator.TEST_AUTHOR);

        authorRepository.save(author);
        actual = author;
    }

    @AfterEach
    private void deleteAll() {
        authorRepository.deleteAll();
    }

    @DisplayName("Get author by id")
    @Test
    void getById() throws Exception {
        // given
        var url = "/authors/{id}";
        // when
        var response = mockMvc.perform(get(url, actual.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, AuthorResponse.class);
        assertThat(actual.getFirstName()).isEqualTo(extend.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(extend.getLastName());
    }

    @DisplayName("Get all authors")
    @Test
    void getAll() throws Exception {
        // given
        var url = "/authors";
        // when
        var response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = Arrays.asList(objectMapper.readValue(response, AuthorResponse[].class));
        assertTrue(extend.size() > 0);
    }

    @DisplayName("Create new author")
    @Test
    void create() throws Exception {
        // given
        var actualDto = AuthorRequest.builder()
                .firstName("Jhon")
                .lastName("Smith")
                .build();
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = post("/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, AuthorResponse.class);
        assertThat(actualDto.getFirstName()).isEqualTo(extend.getFirstName());
        assertThat(actualDto.getLastName()).isEqualTo(actualDto.getLastName());
    }

    @DisplayName("Update author by id")
    @Test
    void update() throws Exception {
        // given
        var actualDto = AuthorRequest.builder()
                .firstName("Jhon")
                .lastName(actual.getLastName())
                .build();
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = put("/authors/{id}", actual.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, AuthorResponse.class);
        assertThat(actualDto.getFirstName()).isEqualTo(extend.getFirstName());
        assertThat(actualDto.getLastName()).isEqualTo(actualDto.getLastName());
    }

    @DisplayName("Delete author by id")
    @Test
    void remove() throws Exception {
        // given
        var url = "/authors/{id}";
        // when, then
        this.mockMvc.perform(delete(url, actual.getId()))
                .andExpect(status()
                        .isOk());
    }
}