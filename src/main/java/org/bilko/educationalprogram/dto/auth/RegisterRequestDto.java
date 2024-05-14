package org.bilko.educationalprogram.dto.auth;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String fullName;
    private String email;
    private String password;
}
