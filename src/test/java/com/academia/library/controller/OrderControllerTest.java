package com.academia.library.controller;

import com.academia.library.cryptor.Cryptor;
import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.mapper.AuthorMapper;
import com.academia.library.mapper.BookMapper;
import com.academia.library.mapper.TagMapper;
import com.academia.library.mapper.UserMapper;
import com.academia.library.model.Author;
import com.academia.library.model.Authority;
import com.academia.library.model.Book;
import com.academia.library.model.Order;
import com.academia.library.model.OrderDetail;
import com.academia.library.model.OrderStatus;
import com.academia.library.model.Role;
import com.academia.library.model.Tag;
import com.academia.library.model.User;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.AuthorityRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.OrderDetailsRepository;
import com.academia.library.repository.OrderRepository;
import com.academia.library.repository.RoleRepository;
import com.academia.library.repository.TagRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.util.AuthorityName;
import com.academia.library.util.RoleNames;
import com.academia.library.util.TestDataCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cryptor cryptor;

    private Order actual;

    private Book book;

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
        this.book = book;

        Authority authority = new Authority();
        authority.setName(AuthorityName.AUTHORITY_ALL);
        authorityRepository.save(authority);

        Role userRole = new Role();
        userRole.setName(RoleNames.USER_ROLE);
        userRole.setAuthorities(Set.of(authority));
        roleRepository.save(userRole);

        User user = userMapper.toEntity(TestDataCreator.INSERT_USER);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(cryptor.encode(user.getEmail()));
        user.setFirstName(cryptor.encode(user.getFirstName()));
        user.setLastName(cryptor.encode(user.getLastName()));
        userRepository.save(user);

        Order order = new Order();
        order.setDeleted(false);
        order.setAmount(new BigDecimal("7.99"));
        order.setOrderStatus(OrderStatus.DRAFT);
        order.setCreateAt(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());
        order.setUser(user);
        orderRepository.save(order);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDeleted(false);
        orderDetail.setBookId(book.getId());
        orderDetail.setCount(1L);
        orderDetail.setOrder(order);
        order.setOrderDetails(List.of(orderDetail));
        orderDetailsRepository.save(orderDetail);
        actual = order;

    }

    @AfterEach
    private void deleteAll() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        tagRepository.deleteAll();
        orderDetailsRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        authorityRepository.deleteAll();
    }

    @WithMockUser(username = "test@example.com", password = "Aa12356")
    @Test
    void findAll() throws Exception {
        // given
        var url = "/orders";
        // when
        var response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extendList = Arrays.asList(objectMapper.readValue(response, OrderResponse[].class));
        assertTrue(extendList.size() > 0);
    }

    @WithMockUser(username = "YWxleEBleGFtcGxlLmNvbQ==", password = "Aa12356")
    @Test
    void findById() throws Exception {
        // given
        var url = "/orders/{id}";
        // when
        var response = mockMvc.perform(get(url, actual.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, OrderResponse.class);
        assertThat(actual.getAmount()).isEqualTo(extend.getAmount());
        assertEquals(actual.getOrderStatus().toString(), extend.getStatus());
    }

    @WithMockUser(username = "YWxleEBleGFtcGxlLmNvbQ==", password = "Aa12356")
    @Test
    void create() throws Exception {
        // given
        var url = "/orders";
        var actualDto = OrderRequest.builder()
                .status("PAID")
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(book.getId())
                                .count(2L)
                                .build())
                        .build()))
                .build();
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, OrderResponse.class);
        assertTrue(extend.getOrderDetails().size() > 0);
        assertThat(actualDto.getStatus()).isEqualTo(extend.getStatus());
    }

    @WithMockUser(username = "YWxleEBleGFtcGxlLmNvbQ==", password = "Aa12356")
    @Test
    void update() throws Exception {
        // given
        var url = "/orders/{id}";
        var actualDto = OrderRequest.builder()
                .status("DRAFT")
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(book.getId())
                                .count(2L)
                                .build())
                        .build()))
                .build();
        var inputJson = objectMapper.writeValueAsString(actualDto);
        var request = put(url, actual.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, OrderResponse.class);
        assertTrue(extend.getOrderDetails().size() > 0);
        assertThat(actualDto.getStatus()).isEqualTo(extend.getStatus());
    }

    @WithMockUser(username = "YWxleEBleGFtcGxlLmNvbQ==", password = "Aa12356")
    @Test
    void updateStatusToPaid() throws Exception {
        // given
        var url = "/orders/paid/{id}";
        var request = put(url, actual.getId());
        // when
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        // then
        var extend = objectMapper.readValue(response, OrderResponse.class);
        assertTrue(extend.getOrderDetails().size() > 0);
        assertThat(OrderStatus.PAID.toString()).isEqualTo(extend.getStatus());
    }

    @WithMockUser(username = "YWxleEBleGFtcGxlLmNvbQ==", roles = "ADMIN", password = "Aa12356")
    @Test
    void updateStatusToDelivered() throws Exception {
        // given
        var url = "/orders/delivered/{id}";
        var request = put(url, actual.getId());
        // when, then
        var response = mockMvc.perform(request)
                .andExpect(status().is(400));
    }
}