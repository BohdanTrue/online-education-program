package org.bilko.educationalprogram.controller;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.course.CourseRequestDto;
import org.bilko.educationalprogram.dto.course.CourseResponseDto;
import org.bilko.educationalprogram.service.CourseService;
import org.springframework.http.HttpStatus;
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

    ///api/courses - GET: Отримати всі курси
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CourseResponseDto> getAll() {
        return courseService.getAll();
    }
    ///api/courses/{id} - GET: Отримати курс за його ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable Long id) {
        return courseService.getById(id);
    }
    ///api/courses - POST: Створити новий курс
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseResponseDto create(@RequestBody CourseRequestDto requestDto) {
        return courseService.create(requestDto);
    }
    ///api/courses/{id} - PUT: Оновити існуючий курс
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CourseResponseDto update(@PathVariable Long id, @RequestBody CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }
    ///api/courses/{id} - DELETE: Видалити курс за його ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.remove(id);
    }
}
