package org.bilko.educationalprogram.dto.module;

import lombok.Data;

@Data
public class ModuleRequestDto {
    private String name;
    private String description;
    private Long courseId;
}
