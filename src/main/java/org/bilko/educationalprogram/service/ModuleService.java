package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.module.ModuleRequestDto;
import org.bilko.educationalprogram.dto.module.ModuleResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModuleService {
    List<ModuleResponseDto> getAll(Pageable pageable);

    ModuleResponseDto getById(Long id);

    ModuleResponseDto create(ModuleRequestDto requestDto);

    ModuleResponseDto update(Long id, ModuleRequestDto requestDto);

    void remove(Long id);
}
