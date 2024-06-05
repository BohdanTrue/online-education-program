package org.bilko.educationalprogram.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.module.ModuleRequestDto;
import org.bilko.educationalprogram.dto.module.ModuleResponseDto;
import org.bilko.educationalprogram.service.ModuleService;
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
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @Operation(summary = "Create a new module", description = "Create a new module")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ModuleResponseDto create(@RequestBody @Valid ModuleRequestDto requestDto) {
        return moduleService.create(requestDto);
    }

    @Operation(summary = "Get all modules", description = "Get a list of all modules")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ModuleResponseDto> getAll(Pageable pageable) {
        return moduleService.getAll(pageable);
    }

    @Operation(summary = "Get a module by id", description = "Get a module by certain id")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ModuleResponseDto getById(@PathVariable Long id) {
        return moduleService.getById(id);
    }

    @Operation(summary = "Update a module by id", description = "Update a module by id, "
            + "if the module doesn't exist, it will throw exception")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ModuleResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid ModuleRequestDto requestDto
    ) {
        return moduleService.update(id, requestDto);
    }

    @Operation(summary = "Delete a module by id", description = "Delete a module by certain id")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        moduleService.remove(id);
    }
}
