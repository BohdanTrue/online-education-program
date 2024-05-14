package org.bilko.educationalprogram.dto.program;

import lombok.Data;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;

@Data
public class ProgramResponseDto {
    private Long id;
    private String name;
    private String description;
    private OrganizationResponseDto organization;
}
