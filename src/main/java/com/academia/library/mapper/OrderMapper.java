package com.academia.library.mapper;

import com.academia.library.dto.request.OrderDetailsRequest;
import com.academia.library.dto.request.OrderRequest;
import com.academia.library.dto.responce.OrderResponse;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.model.Book;
import com.academia.library.model.Order;
import com.academia.library.repository.BookRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class},
        uses = OrderDetailsMapper.class)
public abstract class OrderMapper {

    @Autowired
    private BookRepository bookRepository;

    @Mapping(target = "status", source = "order.orderStatus")
    public abstract OrderResponse toDto(Order order);

    @Mapping(target = "deleted", constant = "false")
    @Mapping(expression = "java(LocalDateTime.now())", target = "createAt")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    @Mapping(target = "orderStatus", source = "orderRequest.status")
    public abstract Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "deleted", constant = "false")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    @Mapping(target = "orderStatus", ignore = true)
    public abstract Order toEntity(OrderRequest orderRequest, @MappingTarget Order order);

    @AfterMapping
    void setAmountAndOrderDetails(@MappingTarget Order order, OrderRequest orderRequest) {
        BigDecimal amount = orderRequest.getOrderDetails().stream()
                .map(this::countTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setAmount(amount);

        order.getOrderDetails()
                .forEach(orderDetail -> orderDetail.setOrder(order));
    }

    private BigDecimal countTotalPrice(OrderDetailsRequest orderDetailsRequest) {
        Long bookId = orderDetailsRequest.getOrderDetailRequestDto().getBookId();

        Book book = bookRepository.getById(bookId);

        return book.getPrice().multiply(new BigDecimal(orderDetailsRequest.getOrderDetailRequestDto().getCount()));
    }
}
