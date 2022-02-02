package com.academia.library.service;

import com.academia.library.dto.AuthRequest;
import com.academia.library.dto.AuthResponse;
import com.academia.library.dto.UserRequest;
import com.academia.library.dto.UserResponse;

public interface UserService {

    AuthResponse login(AuthRequest authRequest);

    UserResponse create(UserRequest userRequest);
}