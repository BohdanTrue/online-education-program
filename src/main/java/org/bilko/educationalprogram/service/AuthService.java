package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.auth.AuthResponseDto;
import org.bilko.educationalprogram.dto.auth.LoginRequestDto;
import org.bilko.educationalprogram.dto.auth.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto requestDto);

    AuthResponseDto login(LoginRequestDto requestDto);
}
