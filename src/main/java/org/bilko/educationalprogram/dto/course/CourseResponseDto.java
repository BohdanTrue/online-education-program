package org.bilko.educationalprogram.dto.course;

import lombok.Data;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;

@Data
public class CourseResponseDto {
    private Long id;
    private String name;
    private String description;
    private ProgramResponseDto program;
//    private int amountOfStudents;
//    private int amountOfModules;
}
