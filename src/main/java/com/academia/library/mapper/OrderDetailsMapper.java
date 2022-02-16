package com.academia.library.mapper;

import com.academia.library.dto.request.OrderDetailsRequest;
import com.academia.library.dto.response.OrderDetailsResponse;
import com.academia.library.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsResponse toDto(OrderDetail orderDetail);

    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "bookId", source = "orderDetailRequestDto.bookId")
    @Mapping(target = "count", source = "orderDetailRequestDto.count")
    OrderDetail toEntity(OrderDetailsRequest request);
}
