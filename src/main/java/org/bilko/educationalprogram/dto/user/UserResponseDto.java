package org.bilko.educationalprogram.dto.user;

import lombok.Data;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private OrganizationResponseDto organization;
    private Set<CourseResponseDto> courses;
}
