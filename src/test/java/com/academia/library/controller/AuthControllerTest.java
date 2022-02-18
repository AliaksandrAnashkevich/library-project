package com.academia.library.controller;

import com.academia.library.cryptor.UserCryptor;
import com.academia.library.dto.response.AuthResponse;
import com.academia.library.dto.request.UserRequest;
import com.academia.library.mapper.UserMapper;
import com.academia.library.model.Authority;
import com.academia.library.model.Role;
import com.academia.library.model.User;
import com.academia.library.repository.AuthorityRepository;
import com.academia.library.repository.RoleRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.util.AuthorityName;
import com.academia.library.util.RoleNames;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCryptor userCryptor;

    @BeforeEach
    private void insertTestData() {
        Authority authority = new Authority();
        authority.setName(AuthorityName.AUTHORITY_ALL);
        authorityRepository.save(authority);

        Role role = new Role();
        role.setName(RoleNames.USER_ROLE);
        role.setAuthorities(Set.of(authority));
        roleRepository.save(role);

        User user = userMapper.toEntity(TestDataCreator.INSERT_USER);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(userCryptor.encode(user.getEmail()));
        user.setFirstName(userCryptor.encode(user.getFirstName()));
        user.setLastName(userCryptor.encode(user.getLastName()));
        userRepository.save(user);
    }

    @AfterEach
    private void deleteAll() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        authorityRepository.deleteAll();
    }

    @DisplayName("Login user")
    @Test
    void login() throws Exception {
        // given
        var actual = TestDataCreator.LOGIN_TEST_USER;
        var requestBody = objectMapper.writeValueAsString(actual);
        var url = "/auth/login";
        // when
        String response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        AuthResponse extend = objectMapper.readValue(response, AuthResponse.class);
        assertEquals(actual.getEmail(), extend.getEmail());
    }

    @DisplayName("Registration user with valid field")
    @Test
    void registrationValidFieldUser() throws Exception {
        // given
        var actual = TestDataCreator.VALID_TEST_USER;
        var requestBody = objectMapper.writeValueAsString(actual);
        var url = "/auth/registration";
        // when
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, UserRequest.class);
        assertThat(actual.getFirstName()).isEqualTo(userCryptor.decode(extend.getFirstName()));
        assertThat(actual.getLastName()).isEqualTo(userCryptor.decode(extend.getLastName()));
        assertThat(actual.getEmail()).isEqualTo(userCryptor.decode(extend.getEmail()));
    }

    @DisplayName("Registration user with invalid field")
    @Test
    void registrationInvalidFieldUser() throws Exception {
        // given
        var actual = TestDataCreator.INVALID_TEST_USER;
        var requestBody = objectMapper.writeValueAsString(actual);
        var url = "/auth/registration";
        // when, then
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(isCondition(result)));
    }

    private boolean isCondition(org.springframework.test.web.servlet.MvcResult result) {
        return result.getResolvedException() instanceof MethodArgumentNotValidException;
    }
}