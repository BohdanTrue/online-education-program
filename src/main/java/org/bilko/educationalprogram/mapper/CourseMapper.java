package org.bilko.educationalprogram.mapper;

import org.bilko.educationalprogram.config.MapperConfig;
import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.bilko.educationalprogram.model.Course;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = ProgramMapper.class)
public interface CourseMapper {
    CourseResponseDto toDto(Course course);

    Course toEntity(CourseRequestDto requestDto);

//    @AfterMapping
//    default void calculateAmountOfStudents(@MappingTarget CourseResponseDto responseDto, Course course) {
//        int amountOfStudents = course.getStudents().size();
//
//        responseDto.setAmountOfStudents(amountOfStudents);
//    }
//
//    @AfterMapping
//    default void calculateAmountOfModules(@MappingTarget CourseResponseDto responseDto, Course course) {
//        int amountOfModules = course.getModules().size();
//
//        responseDto.setAmountOfStudents(amountOfModules);
//    }
}
