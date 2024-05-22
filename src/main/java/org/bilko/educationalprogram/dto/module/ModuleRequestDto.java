package org.bilko.educationalprogram.dto.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModuleRequestDto {
    @NotBlank(message = "module name cannot be null or empty")
    @Size(min = 3, max = 60)
    private String name;
    @NotBlank(message = "description cannot be null or empty")
    private String description;
    private Long courseId;
}
