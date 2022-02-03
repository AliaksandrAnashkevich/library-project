package com.academia.library.controller;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.service.OrderService;
import com.academia.library.util.RoleNames;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("{id}")
    public OrderResponse findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest orderRequest,
                                @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.create(userDetails.getUsername(), orderRequest);
    }

    @PutMapping("{id}")
    public OrderResponse update(@PathVariable("id") Long id,
                                @RequestBody OrderRequest orderRequest) {
        return orderService.update(id, orderRequest);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
