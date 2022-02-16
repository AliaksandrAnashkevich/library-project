package com.academia.library.service;

import com.academia.library.dto.request.OrderRequest;
import com.academia.library.dto.response.OrderResponse;
import com.academia.library.model.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse findById(Long id);

    List<OrderResponse> findAll();

    OrderResponse create(String username, OrderRequest orderRequest);

    OrderResponse update(Long id, OrderRequest orderRequest);

    OrderResponse updateStatus(Long id, OrderStatus newStatus , OrderStatus validStatus);

    void delete(Long id);
}
