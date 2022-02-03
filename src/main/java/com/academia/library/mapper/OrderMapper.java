package com.academia.library.mapper;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.model.Book;
import com.academia.library.model.Order;
import com.academia.library.repository.BookRepository;
import liquibase.pro.packaged.M;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class})
public abstract class OrderMapper {

    @Autowired
    private BookRepository bookRepository;

    @Mapping(target = "status", source = "order.orderStatus")
    public abstract OrderResponse toDto(Order order);

    @Mapping(expression = "java(LocalDateTime.now())", target = "createAt")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    @Mapping(target = "orderStatus", source = "orderRequest.status")
    public abstract Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Order updateRequestToEntity(OrderRequest orderRequest, @MappingTarget Order order);

    @AfterMapping
    void setBooksAndAmount(@MappingTarget Order order, OrderRequest orderRequest) {
        List<Book> books = orderRequest.getBooksId().stream()
                .map(id -> bookRepository.getById(id))
                .collect(Collectors.toList());
        order.setBooks(books);

        BigDecimal amount = books.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(amount);
    }
}
