package com.academia.library.service.impl;

import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.mapper.OrderMapper;
import com.academia.library.model.Order;
import com.academia.library.model.OrderDetail;
import com.academia.library.model.OrderStatus;
import com.academia.library.model.User;
import com.academia.library.repository.OrderDetailsRepository;
import com.academia.library.repository.OrderRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.validator.OrderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @Mock
    OrderMapper orderMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderValidator orderValidator;

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Test
    void findById() {
        // given
        var id = 1L;

        var order = new Order();
        order.setId(id);
        order.setAmount(new BigDecimal("9.99"));
        order.setOrderStatus(OrderStatus.PAID);

        var extend = OrderResponse.builder()
                .id(id)
                .amount(new BigDecimal("9.99"))
                .status(OrderStatus.PAID.name())
                .build();
        // when
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(extend);
        // then
        var actual = orderServiceImpl.findById(id);
        assertThat(actual.getAmount()).isEqualTo(actual.getAmount());
        assertThat(actual.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void findAll() {
        // given
        var id = 1L;

        var order = new Order();
        order.setId(id);
        order.setAmount(new BigDecimal("9.99"));
        order.setOrderStatus(OrderStatus.PAID);

        var extend = OrderResponse.builder()
                .id(id)
                .amount(new BigDecimal("9.99"))
                .status(OrderStatus.PAID.name())
                .build();
        // when
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toDto(order)).thenReturn(extend);
        // then
        var actualList = orderServiceImpl.findAll();
        assertTrue(actualList.size() > 0);
    }

    @Test
    void create() {
        // given
        var id = 1L;
        var username = "test@example.com";

        var order = new Order();
        order.setId(id);
        order.setAmount(new BigDecimal("9.99"));
        order.setOrderStatus(OrderStatus.PAID);

        var insertOrder = OrderRequest.builder()
                .status(OrderStatus.PAID.name())
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest
                                .OrderDetailRequestDto.builder()
                                .bookId(1L)
                                .count(1L)
                                .build())
                        .build()))
                .build();

        var extend = OrderResponse.builder()
                .id(1L)
                .amount(new BigDecimal("9.99"))
                .status(OrderStatus.PAID.name())
                .build();
        // when
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(new User()));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toEntity(insertOrder)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(extend);
        // then
        var actual = orderServiceImpl.create(username, insertOrder);
        assertThat(actual.getAmount()).isEqualTo(actual.getAmount());
        assertThat(actual.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void update() {
        // given
        var id = 1L;

        var beforeUpdateOrder = new Order();
        beforeUpdateOrder.setId(id);
        beforeUpdateOrder.setAmount(new BigDecimal("9.99"));
        beforeUpdateOrder.setOrderStatus(OrderStatus.PAID);

        var afterUpdateOrder = new Order();
        afterUpdateOrder.setId(id);
        afterUpdateOrder.setAmount(new BigDecimal("9.00"));
        afterUpdateOrder.setOrderStatus(OrderStatus.PAID);

        var insertOrder = OrderRequest.builder()
                .status(OrderStatus.PAID.name())
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest
                                .OrderDetailRequestDto.builder()
                                .bookId(1L)
                                .count(1L)
                                .build())
                        .build()))
                .build();

        var extend = OrderResponse.builder()
                .id(1L)
                .amount(new BigDecimal("9.00"))
                .status(OrderStatus.PAID.name())
                .build();
        // when
        when(orderRepository.findById(id)).thenReturn(Optional.of(beforeUpdateOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(afterUpdateOrder);
        when(orderMapper.updateRequestToEntity(insertOrder, beforeUpdateOrder)).thenReturn(afterUpdateOrder);
        when(orderMapper.toDto(afterUpdateOrder)).thenReturn(extend);
        // then
        var actual = orderServiceImpl.update(id, insertOrder);
        assertThat(actual.getAmount()).isEqualTo(actual.getAmount());
        assertThat(actual.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void updateStatusToPaid() {
        // given
        var id = 1L;

        var beforeUpdateOrder = new Order();
        beforeUpdateOrder.setId(id);
        beforeUpdateOrder.setOrderStatus(OrderStatus.DRAFT);

        var afterUpdateOrder = new Order();
        afterUpdateOrder.setId(id);
        afterUpdateOrder.setOrderStatus(OrderStatus.PAID);

        var extend = OrderResponse.builder()
                .id(1L)
                .amount(new BigDecimal("9.00"))
                .status(OrderStatus.PAID.name())
                .build();
        // when
        when(orderRepository.findById(id)).thenReturn(Optional.of(beforeUpdateOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(afterUpdateOrder);
        when(orderMapper.toDto(afterUpdateOrder)).thenReturn(extend);
        // then
        var actual = orderServiceImpl.updateStatus(id, OrderStatus.PAID, OrderStatus.DRAFT);
        assertThat(actual.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void updateStatusToDelivered() {
        // given
        var id = 1L;

        var beforeUpdateOrder = new Order();
        beforeUpdateOrder.setId(id);
        beforeUpdateOrder.setOrderStatus(OrderStatus.PAID);

        var afterUpdateOrder = new Order();
        afterUpdateOrder.setId(id);
        afterUpdateOrder.setOrderStatus(OrderStatus.DELIVERED);

        var extend = OrderResponse.builder()
                .id(1L)
                .amount(new BigDecimal("9.00"))
                .status(OrderStatus.DELIVERED.name())
                .build();
        // when
        when(orderRepository.findById(id)).thenReturn(Optional.of(beforeUpdateOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(afterUpdateOrder);
        when(orderMapper.toDto(afterUpdateOrder)).thenReturn(extend);
        // then
        var actual = orderServiceImpl.updateStatus(id, OrderStatus.DELIVERED, OrderStatus.PAID);
        assertThat(actual.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void delete() {
        // given
        var id = 1L;

        var orderDetail = new OrderDetail();
        orderDetail.setCount(1L);
        orderDetail.setBookId(1L);

        var order = new Order();
        order.setId(id);
        order.setAmount(new BigDecimal("9.99"));
        order.setOrderStatus(OrderStatus.PAID);
        order.setOrderDetails(List.of(orderDetail));
        // when
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        // given
        orderServiceImpl.delete(id);
    }
}