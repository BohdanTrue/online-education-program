package org.bilko.educationalprogram.dto.organization;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrganizationResponseDto {
    private Long id;
    private String name;
    private String description;
}
