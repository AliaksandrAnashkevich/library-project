package com.academia.library.validator;

import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.exception.OrderStatusException;
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

    public void validatorBooks(List<OrderDetailsRequest> orderDetailsRequests) {
        orderDetailsRequests.stream()
                .map(OrderDetailsRequest::getBookId)
                .forEach(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id)));
    }

    public void validatorStatusCreate(String status) {
        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new OrderStatusException(status));

        if (status.equals(OrderStatus.DELIVERED.name())) {
            throw new OrderStatusException(status);
        }
    }

    public void validatorStatusDraft(OrderStatus orderStatus) {
        if (!(orderStatus == (OrderStatus.DRAFT))) {
            throw new OrderStatusException(orderStatus.name());
        }
    }

    public void validatorStatusPaid(OrderStatus orderStatus) {
        if (!(orderStatus == (OrderStatus.PAID))) {
            throw new OrderStatusException(orderStatus.name());
        }
    }
}
