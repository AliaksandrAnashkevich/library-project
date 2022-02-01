package com.academia.library.mapper;

import com.academia.library.dto.UserRequest;
import com.academia.library.dto.UserResponse;
import com.academia.library.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    User toEntity(UserRequest userDto);
}