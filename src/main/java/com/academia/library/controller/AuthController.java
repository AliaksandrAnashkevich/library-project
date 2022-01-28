package com.academia.library.controller;

import com.academia.library.dto.AuthRequestDto;
import com.academia.library.dto.AuthResponseDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;
import com.academia.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "authentication")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Login", description = "Login user")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Invalid email or password.", content = @Content)
    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return userService.login(authRequestDto);
    }

    @Operation(summary = "Registration", description = "Registration new user")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid user request body", content = @Content)
    @PostMapping("/registration")
    public UserResponseDto registration(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.create(userRequestDto);
    }

}
