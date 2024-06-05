package org.bilko.educationalprogram.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.bilko.educationalprogram.service.ProgramService;
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
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;

    @Operation(summary = "Create a new program", description = "Create a new program")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProgramResponseDto create(@RequestBody @Valid ProgramRequestDto requestDto) {
        return programService.create(requestDto);
    }

    @Operation(summary = "Get all programs", description = "Get a list of all programs")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProgramResponseDto> getAll(Pageable pageable) {
        return programService.getAll(pageable);
    }

    @Operation(summary = "Get a program by id", description = "Get a program by certain id")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProgramResponseDto getById(@PathVariable Long id) {
        return programService.getById(id);
    }

    @Operation(summary = "Update a program by id", description = "Update a program by id, "
            + "if the program doesn't exist, it will throw exception")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ProgramResponseDto update(@PathVariable Long id, @RequestBody @Valid ProgramRequestDto requestDto) {
        return programService.update(id, requestDto);
    }

    @Operation(summary = "Delete a program by id", description = "Delete a program by certain id")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        programService.remove(id);
    }
}
