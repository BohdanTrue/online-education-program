package org.bilko.educationalprogram.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseRequestDto {
    @NotBlank(message = "course name cannot be null or empty")
    @Size(min = 3, max = 60)
    private String name;
    @NotBlank(message = "description cannot be null or empty")
    private String description;
    private Long programId;
}
