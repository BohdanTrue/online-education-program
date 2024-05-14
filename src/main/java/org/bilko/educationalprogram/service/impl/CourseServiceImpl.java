package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.bilko.educationalprogram.mapper.CourseMapper;
import org.bilko.educationalprogram.model.Course;
import org.bilko.educationalprogram.model.Program;
import org.bilko.educationalprogram.repository.CourseRepository;
import org.bilko.educationalprogram.repository.ProgramRepository;
import org.bilko.educationalprogram.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponseDto> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    public CourseResponseDto getById(Long id) {
        return courseMapper.toDto(courseRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Cannot find course by id: " + id)
                )
        );
    }

    @Override
    public CourseResponseDto create(CourseRequestDto requestDto) {
        Program program = findProgramById(requestDto.getProgramId());

        Course course = courseMapper.toEntity(requestDto);
        course.setProgram(program);

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto requestDto) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot update course with id: " + id);
        }

        Program program = findProgramById(requestDto.getProgramId());

        Course course = courseMapper.toEntity(requestDto);
        course.setId(id);
        course.setProgram(program);

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public void remove(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete course with id: " + id);
        }

        courseRepository.deleteById(id);
    }

    private Program findProgramById(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find program by id: " + id));
    }
}
