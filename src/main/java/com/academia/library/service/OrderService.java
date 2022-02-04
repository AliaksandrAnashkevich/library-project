package com.academia.library.service;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {

    OrderResponse findById(Long id);

    List<OrderResponse> findAll();

    OrderResponse create(String username, OrderRequest orderRequest);

    OrderResponse update(Long id, OrderRequest orderRequest);

    OrderResponse updateStatusToPaid(Long id);

    OrderResponse updateStatusToDelivered(Long id);

    void delete(Long id);
}
