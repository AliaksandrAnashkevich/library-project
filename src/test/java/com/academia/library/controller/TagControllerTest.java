package com.academia.library.controller;

import com.academia.library.dto.request.TagRequest;
import com.academia.library.dto.response.TagResponse;
import com.academia.library.mapper.TagMapper;
import com.academia.library.model.Tag;
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
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TagMapper tagMapper;

    private Tag actual;

    @BeforeEach
    private void insertTestData() {
        Tag tag = tagMapper.toEntity(TestDataCreator.TEST_TAG);

        tagRepository.save(tag);
        actual = tag;
    }

    @AfterEach
    private void deleteAll() {
        tagRepository.deleteAll();
    }

    @DisplayName("Get tag by id")
    @Test
    void getById() throws Exception {
        // given
        var url = "/tags/{id}";
        // when
        var response = mockMvc.perform(get(url, actual.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, TagResponse.class);
        assertThat(actual.getName()).isEqualTo(extend.getName());
    }

    @DisplayName("Get all tags")
    @Test
    void getAll() throws Exception {
        // given
        var url = "/tags";
        // when
        var response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = Arrays.asList(objectMapper.readValue(response, TagResponse[].class));
        assertTrue(extend.size() > 0);
    }

    @DisplayName("Create new tag")
    @Test
    void create() throws Exception {
        // given
        var actualDto = new TagRequest();
        actualDto.setName("Test");
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = post("/tags")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, TagResponse.class);
        assertThat(actualDto.getName()).isEqualTo(extend.getName());
    }

    @DisplayName("Update book by id")
    @Test
    void update() throws Exception {
        // given
        var actualDto = new TagRequest();
        actualDto.setName("Test");
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = put("/tags/{id}", actual.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, TagResponse.class);
        assertThat(actualDto.getName()).isEqualTo(extend.getName());
    }

    @DisplayName("Delete tag by id")
    @Test
    void remove() throws Exception {
        // given
        var url = "/tags/{id}";
        // when, then
        this.mockMvc.perform(delete(url, actual.getId()))
                .andExpect(status()
                        .isOk());
    }
}