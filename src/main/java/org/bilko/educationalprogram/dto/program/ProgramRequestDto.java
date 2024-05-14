package org.bilko.educationalprogram.dto.program;

import lombok.Data;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;

import java.util.List;
import java.util.Set;

@Data
public class ProgramRequestDto {
    private String name;
    private String description;
    private Long organizationId;
}
