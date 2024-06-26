package org.bilko.educationalprogram.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotBlank(message = "full name cannot be null or empty")
    @Size(min = 3, max = 30)
    private String fullName;
    @Email(message = "invalid email")
    private String email;
    @NotBlank(message = "password cannot be null or empty")
    @Size(min = 3, max = 30)
    private String password;
}
