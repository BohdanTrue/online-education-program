package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.mapper.OrganizationMapper;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationResponseDto getById(Long id) {
        return organizationMapper.toDto(organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find organization by id: " + id)));
    }

    @Override
    public List<OrganizationResponseDto> getAll() {
        return organizationRepository.findAll().stream()
                .map(organizationMapper::toDto)
                .toList();
    }

    @Override
    public OrganizationResponseDto create(OrganizationRequestDto requestDto) {
        return organizationMapper.toDto(organizationRepository.save(organizationMapper.toEntity(requestDto)));
    }

    @Override
    public OrganizationResponseDto update(Long id, OrganizationRequestDto requestDto) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot update organization with id: " + id);
        }

        Organization organization = organizationMapper.toEntity(requestDto);
        organization.setId(id);

        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Override
    public void remove(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot remove organization with id: " + id);
        }

        organizationRepository.deleteById(id);
    }
}
