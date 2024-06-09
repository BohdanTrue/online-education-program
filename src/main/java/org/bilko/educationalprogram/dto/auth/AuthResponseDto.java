package org.bilko.educationalprogram.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponseDto {
    private String jwt;
    private String message;
}