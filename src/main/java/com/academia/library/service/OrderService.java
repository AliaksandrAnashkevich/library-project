package com.academia.library.service;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.model.OrderStatus;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {

    OrderResponse findById(Long id);

    List<OrderResponse> findAll();

    OrderResponse create(String username, OrderRequest orderRequest);

    OrderResponse update(Long id, OrderRequest orderRequest);

    OrderResponse updateStatus(Long id, OrderStatus newStatus , OrderStatus validStatus);

    void delete(Long id);
}
