package org.bilko.educationalprogram.controller;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.service.OrganizationService;
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
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    ///api/organizations - GET: Отримати всі організації
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrganizationResponseDto> getAll() {
        return organizationService.getAll();
    }
    ///api/organizations/{id} - GET: Отримати організацію за її ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrganizationResponseDto getById(@PathVariable Long id) {
        return organizationService.getById(id);
    }
    ///api/organizations - POST: Створити нову організацію
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrganizationResponseDto create(@RequestBody OrganizationRequestDto requestDto) {
        return organizationService.create(requestDto);
    }

    ///api/organizations/{id} - PUT: Оновити існуючу організацію
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public OrganizationResponseDto update(@PathVariable Long id, @RequestBody OrganizationRequestDto requestDto) {
        return organizationService.update(id, requestDto);
    }
    ///api/organizations/{id} - DELETE: Видалити організацію за її ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        organizationService.remove(id);
    }
}
