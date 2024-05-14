package org.bilko.educationalprogram.mapper;

import org.bilko.educationalprogram.config.MapperConfig;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.model.Organization;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrganizationMapper {
    Organization toEntity(OrganizationRequestDto requestDto);

    OrganizationResponseDto toDto(Organization organization);
}
