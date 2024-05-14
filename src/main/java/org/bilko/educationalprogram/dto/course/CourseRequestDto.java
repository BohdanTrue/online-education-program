package org.bilko.educationalprogram.dto.course;

import lombok.Data;

@Data
public class CourseRequestDto {
    private String name;
    private String description;
    private Long programId;
}
