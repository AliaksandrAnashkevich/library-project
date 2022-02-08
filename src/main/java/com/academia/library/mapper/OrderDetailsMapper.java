package com.academia.library.mapper;

import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderDetailsResponse;
import com.academia.library.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsResponse toDto(OrderDetail orderDetail);

    @Named("toEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "bookId", expression = "java(orderDetailsRequest.getOrderDetailRequestDto().getBookId())")
    @Mapping(target = "count", expression = "java(orderDetailsRequest.getOrderDetailRequestDto().getCount())")
    OrderDetail toEntity(OrderDetailsRequest orderDetailsRequest);

    @Named("updateRequestToEntity")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "bookId", expression = "java(orderDetailsRequest.getOrderDetailRequestDto().getBookId())")
    @Mapping(target = "count", expression = "java(orderDetailsRequest.getOrderDetailRequestDto().getCount())")
    OrderDetail updateRequestToEntity(OrderDetailsRequest orderDetailsRequest);
}
