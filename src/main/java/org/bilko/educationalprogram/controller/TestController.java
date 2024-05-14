package org.bilko.educationalprogram.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.mapper.OrganizationMapper;
import org.bilko.educationalprogram.mapper.ProgramMapper;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.repository.ProgramRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public OrganizationResponseDto getAll() {
        return organizationMapper.toDto(organizationRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Cannotwae")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ch")
    public ProgramResponseDto getAll2() {
        return programMapper.toDto(programRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Cannotwae")));
    }
}
