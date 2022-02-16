package com.academia.library.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrderResponse {

    private final Long id;
    private final BigDecimal amount;
    private final String status;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final UserResponse user;
    private final List<OrderDetailsResponse> orderDetails;
}
