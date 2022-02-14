package com.academia.library.mapper;

import com.academia.library.dto.request.OrderDetailsRequest;
import com.academia.library.dto.request.OrderRequest;
import com.academia.library.model.Book;
import com.academia.library.model.Order;
import com.academia.library.model.OrderDetail;
import com.academia.library.model.OrderStatus;
import com.academia.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderMapperTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    OrderDetailsMapperImpl orderDetailsMapper;

    @InjectMocks
    OrderMapperImpl orderMapper;

    @Test
    void toDto() {
        // given
        var amount = new BigDecimal("9.99");
        var status = OrderStatus.PAID;

        var order = new Order();
        order.setAmount(amount);
        order.setOrderStatus(status);
        // when
        var actual = orderMapper.toDto(order);
        // then
        assertThat(actual.getAmount()).isEqualTo(amount);
        assertThat(actual.getStatus()).isEqualTo(status.name());
    }

    @Test
    void toEntity() {
        // given
        var status = OrderStatus.DRAFT;
        var bookId = 1L;
        var count = 1L;
        var price = new BigDecimal("9.99");

        var orderRequest = OrderRequest.builder()
                .status(status.name())
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();

        var orderDetail = new OrderDetail();
        orderDetail.setBookId(bookId);
        orderDetail.setCount(count);

        var book = new Book();
        book.setId(bookId);
        book.setPrice(price);
        // when
        when(bookRepository.getById(bookId)).thenReturn(book);
        when(orderDetailsMapper.toEntity(any(OrderDetailsRequest.class))).thenReturn(orderDetail);
        var actual = orderMapper.toEntity(orderRequest);
        // then
        assertThat(actual.getAmount()).isEqualTo(price);
        assertThat(actual.getOrderStatus()).isEqualTo(status);
    }

    @Test
    void testToEntity() {
        // given
        var status = OrderStatus.DRAFT;
        var orderDetailId = 1L;
        var bookId = 1L;
        var count = 1L;
        var price = new BigDecimal("9.99");

        var orderRequest = OrderRequest.builder()
                .status(status.name())
                .orderDetails(List.of(OrderDetailsRequest.builder()
                        .id(orderDetailId)
                        .orderDetailRequestDto(OrderDetailsRequest.OrderDetailRequestDto.builder()
                                .bookId(bookId)
                                .count(count)
                                .build())
                        .build()))
                .build();

        var orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailId);
        orderDetail.setBookId(bookId);
        orderDetail.setCount(count);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        var order = new Order();
        order.setAmount(new BigDecimal("5.55"));
        order.setOrderStatus(status);
        order.setOrderDetails(orderDetailList);

        var book = new Book();
        book.setId(bookId);
        book.setPrice(price);
        // when
        when(bookRepository.getById(bookId)).thenReturn(book);
        when(orderDetailsMapper.toEntity(any(OrderDetailsRequest.class))).thenReturn(orderDetail);
        var actual = orderMapper.toEntity(orderRequest, order);
        // then
        assertThat(actual.getAmount()).isEqualTo(price);
        assertThat(actual.getOrderStatus()).isEqualTo(status);
    }
}