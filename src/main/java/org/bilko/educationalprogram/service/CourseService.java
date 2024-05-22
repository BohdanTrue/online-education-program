package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    List<CourseResponseDto> getAll(Pageable pageable);

    CourseResponseDto getById(Long id);

    CourseResponseDto create(CourseRequestDto requestDto);

    CourseResponseDto update(Long id, CourseRequestDto requestDto);

    void remove(Long id);
}

