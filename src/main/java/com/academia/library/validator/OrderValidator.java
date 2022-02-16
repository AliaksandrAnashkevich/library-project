package com.academia.library.validator;

import com.academia.library.dto.request.OrderRequest;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.exception.OrderStatusException;
import com.academia.library.model.Order;
import com.academia.library.model.OrderStatus;
import com.academia.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final BookRepository bookRepository;

    public void validateBeforeCreate(OrderRequest orderRequest) {
        String status = orderRequest.getStatus();
        validateStringStatusWithoutDelivered(status);

        validateBooks(orderRequest);
    }

    public void validateBeforeUpdate(OrderRequest orderRequest, Order order) {
        validateBeforeUpdate(order, OrderStatus.DRAFT);

        validateBooks(orderRequest);
    }

    public void validateBeforeUpdate(Order order, OrderStatus extendStatus) {
        OrderStatus orderStatus = order.getOrderStatus();

        if (orderStatus != extendStatus) {
            throw new OrderStatusException(orderStatus.name());
        }
    }

    private void validateBooks(OrderRequest orderRequest) {
        List<Long> bookIds = orderRequest.getOrderDetails().stream()
                .map(orderDetail -> orderDetail.getOrderDetailRequestDto().getBookId())
                .collect(Collectors.toList());

        bookIds.forEach(id -> bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id)));
    }

    private void validateStringStatusWithoutDelivered(String status) {
        OrderStatus checkStatus = OrderStatus.getByStatus(status);

        if (checkStatus == OrderStatus.DELIVERED) {
            throw new OrderStatusException(status);
        }
    }
}