package org.bilko.educationalprogram.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String jwt;
    private String message;
}