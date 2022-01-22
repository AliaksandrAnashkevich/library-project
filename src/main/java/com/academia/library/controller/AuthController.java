package com.academia.library.controller;

import com.academia.library.config.PasswordConfig;
import com.academia.library.dto.AuthRequestDto;
import com.academia.library.dto.AuthResponseDto;
import com.academia.library.dto.UserDto;
import com.academia.library.security.JwtTokenProvider;
import com.academia.library.security.JwtUser;
import com.academia.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final PasswordConfig passwordConfig;
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
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    @PostMapping("/registration")
    public AuthResponseDto registration(@RequestBody UserDto userDto) {

        if (userService.existByEmail(userDto.getEmail())) {
            throw new RuntimeException();
        }
        userDto.setPassword(passwordConfig
                .passwordEncoder()
                .encode(userDto.getPassword()));
        userService.save(userDto);


        return AuthResponseDto.builder()
                .email(userDto.getEmail())
                .token("token")
                .build();
    }
}
