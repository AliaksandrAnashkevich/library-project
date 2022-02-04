package com.academia.library.mapper;

import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderDetailsResponse;
import com.academia.library.model.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsResponse toDto(OrderDetail orderDetail);

    OrderDetail toEntity(OrderDetailsRequest orderDetailsRequest);
}
