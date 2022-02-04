package com.academia.library.mapper;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderResponse;
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
        imports = {LocalDateTime.class})
public abstract class OrderMapper {

    @Autowired
    private BookRepository bookRepository;

    @Mapping(target = "status", source = "order.orderStatus")
    public abstract OrderResponse toDto(Order order);

    @Mapping(target = "orderDetails.id", ignore = true)
    @Mapping(expression = "java(LocalDateTime.now())", target = "createAt")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    @Mapping(target = "orderStatus", source = "orderRequest.status")
    public abstract Order toEntity(OrderRequest orderRequest);

    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Order updateRequestToEntity(OrderRequest orderRequest, @MappingTarget Order order);

    @AfterMapping
    void setAmount(@MappingTarget Order order, OrderRequest orderRequest) {
        BigDecimal amount = orderRequest.getOrderDetails().stream()
                .map(this::countTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setAmount(amount);
    }

    private BigDecimal countTotalPrice(OrderDetailsRequest orderDetailsRequest) {
        Book book = bookRepository.findById(orderDetailsRequest.getBookId())
                .orElseThrow(() -> new BookNotFoundException(orderDetailsRequest.getBookId()));

        return book.getPrice().multiply(new BigDecimal(orderDetailsRequest.getCount()));
    }

}
