package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class OrderRequest {

    private final String status;
    private final List<OrderDetailsRequest> orderDetails;
}
