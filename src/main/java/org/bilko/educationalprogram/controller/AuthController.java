package org.bilko.educationalprogram.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.auth.AuthResponseDto;
import org.bilko.educationalprogram.dto.auth.LoginRequestDto;
import org.bilko.educationalprogram.dto.auth.RegisterRequestDto;
import org.bilko.educationalprogram.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Register a new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public AuthResponseDto register(@RequestBody @Valid RegisterRequestDto requestDto) {
        return authService.register(requestDto);
    }

    @Operation(summary = "Login an exists user", description = "Login an exists user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }
}
