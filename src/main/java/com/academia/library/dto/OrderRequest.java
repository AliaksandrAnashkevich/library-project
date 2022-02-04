package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderRequest {

    private final String status;
    private final List<OrderDetailsRequest> orderDetails;
}
