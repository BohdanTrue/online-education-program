package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;

import java.util.List;

public interface CourseService {

    List<CourseResponseDto> getAll();

    CourseResponseDto getById(Long id);

    CourseResponseDto create(CourseRequestDto requestDto);

    CourseResponseDto update(Long id, CourseRequestDto requestDto);

    void remove(Long id);
}

