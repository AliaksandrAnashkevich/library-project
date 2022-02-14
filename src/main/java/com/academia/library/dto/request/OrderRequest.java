package com.academia.library.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
public class OrderRequest {

    private final String status;
    private final List<OrderDetailsRequest> orderDetails;
}
