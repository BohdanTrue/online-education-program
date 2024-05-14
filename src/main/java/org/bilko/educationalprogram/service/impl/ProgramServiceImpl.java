package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.bilko.educationalprogram.mapper.ProgramMapper;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.model.Program;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.repository.ProgramRepository;
import org.bilko.educationalprogram.service.ProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final OrganizationRepository organizationRepository;

    @Override
    public List<ProgramResponseDto> getAll() {
        return programRepository.findAll().stream()
                .map(programMapper::toDto)
                .toList();
    }

    @Override
    public ProgramResponseDto getById(Long id) {
        return programMapper.toDto(programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find program by id: " + id)));
    }

    @Override
    public ProgramResponseDto create(ProgramRequestDto requestDto) {
        Organization organization = findOrganizationById(requestDto.getOrganizationId());

        Program program = programMapper.toEntity(requestDto);
        program.setOrganization(organization);

        return programMapper.toDto(programRepository.save(program));
    }

    @Override
    @Transactional
    public ProgramResponseDto update(Long id, ProgramRequestDto requestDto) {
        if (!programRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot update program with id: " + id);
        }

        Organization organization = findOrganizationById(requestDto.getOrganizationId());

        Program program = programMapper.toEntity(requestDto);
        program.setId(id);
        program.setOrganization(organization);

        return programMapper.toDto(programRepository.save(program));
    }

    @Override
    public void remove(Long id) {
        if (!programRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot remove program with id: " + id);
        }

        programRepository.deleteById(id);
    }

    private Organization findOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find organization by id: " + id));
    }
}
