package org.bilko.educationalprogram.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.bilko.educationalprogram.service.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CourseResponseDto> getAll(Pageable pageable) {
        return courseService.getAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseResponseDto create(@RequestBody @Valid CourseRequestDto requestDto) {
        return courseService.create(requestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CourseResponseDto update(@PathVariable Long id, @RequestBody @Valid CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.remove(id);
    }
}
