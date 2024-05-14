package org.bilko.educationalprogram.controller;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.module.ModuleRequestDto;
import org.bilko.educationalprogram.dto.module.ModuleResponseDto;
import org.bilko.educationalprogram.service.ModuleService;
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
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    ///api/modules - GET: Отримати всі модулі
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ModuleResponseDto> getAll() {
        return moduleService.getAll();
    }
    ///api/modules/{id} - GET: Отримати модуль за його ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ModuleResponseDto getById(@PathVariable Long id) {
        return moduleService.getById(id);
    }
    ///api/modules - POST: Створити новий модуль
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ModuleResponseDto create(@RequestBody ModuleRequestDto requestDto) {
        return moduleService.create(requestDto);
    }
    ///api/modules/{id} - PUT: Оновити існуючий модуль
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ModuleResponseDto update(@PathVariable Long id, @RequestBody ModuleRequestDto requestDto) {
        return moduleService.update(id, requestDto);
    }
    ///api/modules/{id} - DELETE: Видалити модуль за його ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        moduleService.remove(id);
    }
}
