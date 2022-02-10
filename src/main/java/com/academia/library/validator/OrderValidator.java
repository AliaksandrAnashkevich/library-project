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
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final BookRepository bookRepository;

    public void validateBeforeCreate(OrderRequest orderRequest) {
        String status = orderRequest.getStatus();
        validateStringStatus(status);

        validateBooks(orderRequest);
    }

    public void validateBeforeUpdate(OrderRequest orderRequest, Order order) {
        validateBeforeUpdateStatus(order, OrderStatus.DRAFT);

        validateBooks(orderRequest);
    }

    public void validateBeforeUpdateStatus(Order order, OrderStatus extendStatus){
        OrderStatus orderStatus = order.getOrderStatus();

        if (checkStatus(orderStatus, extendStatus)){
            throw new OrderStatusException(orderStatus.name());
        }
    }

    private boolean checkStatus(OrderStatus actualStatus, OrderStatus extendStatus) {
        return actualStatus != extendStatus;
    }

    private void validateBooks(OrderRequest orderRequest) {
        List<Long> bookIds = orderRequest.getOrderDetails().stream()
                .map(a -> a.getOrderDetailRequestDto().getBookId())
                .collect(Collectors.toList());

        bookIds.forEach(id -> bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id)));
    }

    private void validateStringStatus(String status) {
        if (!EnumUtils.isValidEnum(OrderStatus.class, status)
                && !checkStatus(OrderStatus.valueOf(status), OrderStatus.DELIVERED)) {
            throw new OrderStatusException(status);
        }
    }
}
