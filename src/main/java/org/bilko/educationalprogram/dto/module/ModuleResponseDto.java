package org.bilko.educationalprogram.dto.module;

import lombok.Data;

@Data
public class ModuleResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long courseId;
}
