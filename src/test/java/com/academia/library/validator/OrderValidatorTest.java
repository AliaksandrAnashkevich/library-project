package com.academia.library.validator;

import com.academia.library.dto.request.OrderDetailsRequest;
import com.academia.library.dto.request.OrderRequest;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.exception.OrderStatusException;
import com.academia.library.model.Book;
import com.academia.library.model.Order;
import com.academia.library.model.OrderStatus;
import com.academia.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderValidatorTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    OrderValidator orderValidator;

    @Test
    void validateBeforeCreateSuccess() {
        // given
        var bookId = 1L;
        var count = 1L;
        var orderStatus = OrderStatus.DRAFT.name();

        var book = new Book();

        var orderRequest = OrderRequest.builder()
                .status(orderStatus)
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();
        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        // then
        orderValidator.validateBeforeCreate(orderRequest);

    }

    @Test
    void validateBeforeUpdateEntitySuccess() {
        // given
        var bookId = 1L;
        var count = 1L;
        var orderStatus = OrderStatus.DRAFT;

        var book = new Book();

        var order = new Order();
        order.setOrderStatus(orderStatus);

        var orderRequest = OrderRequest.builder()
                .status(orderStatus.name())
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();
        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        // then
        orderValidator.validateBeforeUpdate(orderRequest, order);
    }

    @Test
    void testBeforeUpdateStatusSuccess() {
        var orderStatus = OrderStatus.PAID;
        var executeStatus = OrderStatus.PAID;

        var order = new Order();
        order.setOrderStatus(orderStatus);

        // when, then
        orderValidator.validateBeforeUpdate(order, executeStatus);
    }

    @Test
    void validateBeforeCreateException() {
        // given
        var bookId = 1L;
        var count = 1L;
        var orderStatus = "OrderStatus.DRAFT";

        var book = new Book();

        var orderRequest = OrderRequest.builder()
                .status(orderStatus)
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();
        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        // then
        assertThrows(OrderStatusException.class, () -> orderValidator.validateBeforeCreate(orderRequest));
    }

    @Test
    void validateBeforeUpdateEntityException() {
        // given
        var bookId = 1L;
        var count = 1L;
        String orderStatus = OrderStatus.DRAFT.name();

        var book = new Book();

        var orderRequest = OrderRequest.builder()
                .status(orderStatus)
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();
        // when
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        // then
        assertThrows(BookNotFoundException.class, () -> orderValidator.validateBeforeCreate(orderRequest));
    }

    @Test
    void testBeforeUpdateStatusException() {
        // given
        var orderStatus = OrderStatus.DELIVERED;
        var executeStatus = OrderStatus.DRAFT;

        var order = new Order();
        order.setOrderStatus(orderStatus);
        // when, then
        assertThrows(OrderStatusException.class, () -> orderValidator.validateBeforeUpdate(order, executeStatus));
    }
}