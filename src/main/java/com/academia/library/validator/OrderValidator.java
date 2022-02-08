package com.academia.library.validator;

import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderDetailsRequest.OrderDetailRequestDto;
import com.academia.library.dto.OrderRequest;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.exception.OrderStatusException;
import com.academia.library.model.Order;
import com.academia.library.model.OrderStatus;
import com.academia.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final BookRepository bookRepository;

    public void validatorBooks(OrderRequest orderRequest) {
        List<OrderDetailsRequest> orderDetailsRequests = orderRequest.getOrderDetails();

        orderDetailsRequests.stream()
                .map(OrderDetailsRequest::getOrderDetailRequestDto)
                .map(OrderDetailRequestDto::getBookId)
                .forEach(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id)));
    }

    public void validatorStatusCreate(OrderRequest orderRequest) {
        String status = orderRequest.getStatus();

        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new OrderStatusException(status));

        if (status.equals(OrderStatus.DELIVERED.name())) {
            throw new OrderStatusException(status);
        }
    }

    public void validatorStatusDraft(Order order) {
        OrderStatus orderStatus = order.getOrderStatus();

        if (validatorStatus(orderStatus, OrderStatus.DRAFT)) {
            throw new OrderStatusException(orderStatus.name());
        }
    }

    public void validatorStatusPaid(Order order) {
        OrderStatus orderStatus = order.getOrderStatus();

        if (validatorStatus(orderStatus, OrderStatus.PAID)) {
            throw new OrderStatusException(orderStatus.name());
        }
    }

    private boolean validatorStatus(OrderStatus actualStatus, OrderStatus extendStatus) {
        return actualStatus != extendStatus;
    }
}
