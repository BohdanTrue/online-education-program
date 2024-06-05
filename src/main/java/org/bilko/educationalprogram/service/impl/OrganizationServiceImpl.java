package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.mapper.OrganizationMapper;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.service.OrganizationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private static final String CANNOT_FIND_ORGANIZATION_BY_ID = "Cannot find organization by id: ";
    private static final String CANNOT_UPDATE_ORGANIZATION = "Cannot update organization with id: ";
    private static final String CANNOT_REMOVE_ORGANIZATION = "Cannot remove organization with id: ";
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationResponseDto getById(Long id) {
        return organizationMapper.toDto(organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_ORGANIZATION_BY_ID + id)));
    }

    @Override
    public List<OrganizationResponseDto> getAll(Pageable pageable) {
        return organizationRepository.findAll(pageable).stream()
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
            throw new EntityNotFoundException(CANNOT_UPDATE_ORGANIZATION + id);
        }

        Organization organization = organizationMapper.toEntity(requestDto);
        organization.setId(id);

        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Override
    public void remove(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException(CANNOT_REMOVE_ORGANIZATION + id);
        }

        organizationRepository.deleteById(id);
    }
}
