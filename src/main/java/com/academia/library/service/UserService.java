package com.academia.library.service;

import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;

public interface UserService {

    UserResponseDto add(UserRequestDto userRequestDto);
}
