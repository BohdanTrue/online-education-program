package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgramService {
    List<ProgramResponseDto> getAll(Pageable pageable);

    ProgramResponseDto getById(Long id);

    ProgramResponseDto create(ProgramRequestDto requestDto);

    ProgramResponseDto update(Long id, ProgramRequestDto requestDto);

    void remove(Long id);
}
