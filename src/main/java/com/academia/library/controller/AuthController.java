package com.academia.library.controller;

import com.academia.library.dto.AuthRequestDto;
import com.academia.library.dto.AuthResponseDto;
import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;
import com.academia.library.exeption.InvalidAuthRequestDataException;
import com.academia.library.security.JwtTokenProvider;
import com.academia.library.security.JwtUser;
import com.academia.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String password = requestDto.getPassword();

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(email, jwtUser.getRoles());

            return AuthResponseDto.builder()
                    .email(email)
                    .token(token)
                    .build();

        } catch (Exception ex) {
            throw new InvalidAuthRequestDataException();
        }
    }

    @PostMapping("/registration")
    public UserResponseDto registration(@RequestBody UserRequestDto userRequestDto) {
        return userService.add(userRequestDto);
    }
}
