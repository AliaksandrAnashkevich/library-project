package com.academia.library.service;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse findById(Long id);

    List<OrderResponse> findAll();

    OrderResponse create(String username, OrderRequest orderRequest);

    OrderResponse update(Long id, OrderRequest orderRequest);

    void delete(Long id);
}
