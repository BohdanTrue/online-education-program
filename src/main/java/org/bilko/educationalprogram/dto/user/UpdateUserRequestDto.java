package org.bilko.educationalprogram.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String fullName;
    private String email;
    private String password;
}
