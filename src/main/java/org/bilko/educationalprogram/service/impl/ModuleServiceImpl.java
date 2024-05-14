package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.module.ModuleRequestDto;
import org.bilko.educationalprogram.dto.module.ModuleResponseDto;
import org.bilko.educationalprogram.mapper.ModuleMapper;
import org.bilko.educationalprogram.model.Course;
import org.bilko.educationalprogram.model.Module;
import org.bilko.educationalprogram.repository.CourseRepository;
import org.bilko.educationalprogram.repository.ModuleRepository;
import org.bilko.educationalprogram.service.ModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleMapper moduleMapper;

    @Override
    public List<ModuleResponseDto> getAll() {
        return moduleRepository.findAll().stream()
                .map(moduleMapper::toDto)
                .toList();
    }

    @Override
    public ModuleResponseDto getById(Long id) {
        return moduleMapper.toDto(moduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find module by id: " + id)));
    }

    @Override
    public ModuleResponseDto create(ModuleRequestDto requestDto) {
        Module module = moduleMapper.toEntity(requestDto);

        Course course = findCourseById(requestDto.getCourseId());

        module.setCourse(course);

        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Override
    public ModuleResponseDto update(Long id, ModuleRequestDto requestDto) {
        if (!moduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot update module with id: " + id);
        }

        Course course = findCourseById(requestDto.getCourseId());
        Module module = moduleMapper.toEntity(requestDto);
        module.setId(id);
        module.setCourse(course);

        return moduleMapper.toDto(moduleRepository.save(module));
    }

    @Override
    public void remove(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot remove module with id: " + id);
        }

        moduleRepository.deleteById(id);
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find course by id: " + id));

    }
}
