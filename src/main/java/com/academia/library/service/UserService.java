package com.academia.library.service;

import com.academia.library.dto.AuthRequestDto;
import com.academia.library.dto.AuthResponseDto;
import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;

public interface UserService {

    AuthResponseDto login(AuthRequestDto authRequestDto);

    UserResponseDto create(UserRequestDto userRequestDto);
}
