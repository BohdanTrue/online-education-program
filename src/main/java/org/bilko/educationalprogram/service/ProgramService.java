package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.program.ProgramRequestDto;
import org.bilko.educationalprogram.dto.program.ProgramResponseDto;

import java.util.List;

public interface ProgramService {
    List<ProgramResponseDto> getAll();

    ProgramResponseDto getById(Long id);

    ProgramResponseDto create(ProgramRequestDto requestDto);

    ProgramResponseDto update(Long id, ProgramRequestDto requestDto);

    void remove(Long id);
}
