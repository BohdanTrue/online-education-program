package org.bilko.educationalprogram.dto.module;

import lombok.Data;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;

@Data
public class ModuleResponseDto {
    private Long id;
    private String name;
    private String description;
    private CourseResponseDto course;
}
