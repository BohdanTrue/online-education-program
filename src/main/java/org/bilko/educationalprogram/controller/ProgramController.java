package org.bilko.educationalprogram.controller;

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

    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProgramResponseDto> getAll(Pageable pageable) {
        return programService.getAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProgramResponseDto getById(@PathVariable Long id) {
        return programService.getById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProgramResponseDto create(@RequestBody @Valid ProgramRequestDto requestDto) {
        return programService.create(requestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ProgramResponseDto update(@PathVariable Long id, @RequestBody @Valid ProgramRequestDto requestDto) {
        return programService.update(id, requestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        programService.remove(id);
    }
}
