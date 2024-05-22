package org.bilko.educationalprogram.dto.program;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProgramRequestDto {
    @NotBlank(message = "program name cannot be null or empty")
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank(message = "description cannot be null or empty")
    private String description;
    private Long organizationId;
}
