package com.academia.library.model;

import com.academia.library.exception.OrderStatusNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OrderStatus {
    DRAFT,
    PAID,
    DELIVERED;

    private static final Map<String, OrderStatus> ENUM_MAP = Stream.of(values())
            .collect(Collectors.toMap(OrderStatus::name, Function.identity()));

    public static OrderStatus getByStatus(String value) {
        return Optional.of(value)
                .map(String::toUpperCase)
                .map(ENUM_MAP::get)
                .orElseThrow(() -> new OrderStatusNotFoundException(value));
    }

}
