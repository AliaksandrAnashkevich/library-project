package com.academia.library.service.impl;

import com.academia.library.cryptor.Cryptor;
import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.exception.OrderNotFoundException;
import com.academia.library.exception.UserNotFoundException;
import com.academia.library.mapper.OrderMapper;
import com.academia.library.model.Order;
import com.academia.library.model.User;
import com.academia.library.repository.OrderRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.service.OrderService;
import com.academia.library.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Cryptor cryptor;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse create(String username, OrderRequest orderRequest) {
        orderValidator.validatorStatusCreate(orderRequest.getStatus());
        orderValidator.validatorBooks(orderRequest.getBooksId());

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Order order = orderMapper.toEntity(orderRequest);
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse update(Long id, OrderRequest orderRequest) {
        orderValidator.validatorBooks(orderRequest.getBooksId());

        Order order = orderRepository.findById(id)
                .map(o -> orderMapper.updateRequestToEntity(orderRequest, o))
                .orElseThrow(() -> new OrderNotFoundException(id));

        Order updateOrder = orderRepository.save(order);

        return orderMapper.toDto(updateOrder);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}
