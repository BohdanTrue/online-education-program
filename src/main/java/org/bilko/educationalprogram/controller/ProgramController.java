package org.bilko.educationalprogram.controller;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.bilko.educationalprogram.service.ProgramService;
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
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;
    ///api/programs - GET: Отримати всі програми
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProgramResponseDto> getAll(){
        return programService.getAll();
    }
    ///api/programs/{id} - GET: Отримати програму за її ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProgramResponseDto getById(@PathVariable Long id) {
        return programService.getById(id);
    }
    ///api/programs - POST: Створити нову програму
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProgramResponseDto create(@RequestBody ProgramRequestDto requestDto) {
        return programService.create(requestDto);
    }
    ///api/programs/{id} - PUT: Оновити існуючу програму
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ProgramResponseDto update(@PathVariable Long id, @RequestBody ProgramRequestDto requestDto) {
        return programService.update(id, requestDto);
    }
    ///api/programs/{id} - DELETE: Видалити програму за її ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        programService.remove(id);
    }
}
