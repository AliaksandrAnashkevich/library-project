package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@ToString
public class OrderRequest {

    private final String history;
    private final String status;
    private final List<Long> booksId;
}
