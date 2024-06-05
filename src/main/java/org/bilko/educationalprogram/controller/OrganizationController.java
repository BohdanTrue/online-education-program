package org.bilko.educationalprogram.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.service.OrganizationService;
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
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @Operation(summary = "Create a new organization", description = "Create a new organization")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrganizationResponseDto create(@RequestBody @Valid OrganizationRequestDto requestDto) {
        return organizationService.create(requestDto);
    }

    @Operation(summary = "Get all organizations", description = "Get a list of all organizations")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrganizationResponseDto> getAll(Pageable pageable) {
        return organizationService.getAll(pageable);
    }

    @Operation(summary = "Get an organization by id", description = "Get an organization by certain id")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrganizationResponseDto getById(@PathVariable Long id) {
        return organizationService.getById(id);
    }

    @Operation(summary = "Update an organization by id", description = "Update an organization by id, "
            + "if the organization doesn't exist, it will throw exception")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public OrganizationResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid OrganizationRequestDto requestDto
    ) {
        return organizationService.update(id, requestDto);
    }

    @Operation(summary = "Delete an organization by id", description = "Delete an organization by certain id")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        organizationService.remove(id);
    }
}
