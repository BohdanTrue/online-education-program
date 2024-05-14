package org.bilko.educationalprogram.mapper;

import org.bilko.educationalprogram.config.MapperConfig;
import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.bilko.educationalprogram.model.Program;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = OrganizationMapper.class)
public interface ProgramMapper {
    Program toEntity(ProgramRequestDto requestDto);

    ProgramResponseDto toDto(Program program);
}
