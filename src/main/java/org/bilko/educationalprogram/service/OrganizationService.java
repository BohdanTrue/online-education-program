package org.bilko.educationalprogram.service;


import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    OrganizationResponseDto getById(Long id);

    List<OrganizationResponseDto> getAll(Pageable pageable);

    OrganizationResponseDto create(OrganizationRequestDto requestDto);

    OrganizationResponseDto update(Long id, OrganizationRequestDto requestDto);

    void remove(Long id);
}
