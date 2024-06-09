package org.bilko.educationalprogram.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrganizationRequestDto {
    @NotBlank(message = "organization name cannot be null or empty")
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank(message = "description cannot be null or empty")
    private String description;
}
