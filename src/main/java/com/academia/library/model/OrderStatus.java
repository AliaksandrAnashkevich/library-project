package com.academia.library.model;

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

    public static Optional<OrderStatus> getByStatus(String status) {
        return Optional.ofNullable(ENUM_MAP.get(status));
    }

}
