package org.bilko.educationalprogram.dto.auth;

import lombok.Data;
import org.bilko.educationalprogram.model.Role;

@Data
public class AuthResponseDto {
    private String jwt;
    private String message;
}