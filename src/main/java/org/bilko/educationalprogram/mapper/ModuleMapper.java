package org.bilko.educationalprogram.mapper;

import org.bilko.educationalprogram.config.MapperConfig;
import org.bilko.educationalprogram.dto.module.ModuleRequestDto;
import org.bilko.educationalprogram.dto.module.ModuleResponseDto;
import org.bilko.educationalprogram.model.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ModuleMapper {
    @Mapping(target = "courseId", source = "course.id")
    ModuleResponseDto toDto(Module module);

    Module toEntity(ModuleRequestDto requestDto);
}
