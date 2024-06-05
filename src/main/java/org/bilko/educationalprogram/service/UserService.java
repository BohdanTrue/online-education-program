package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.user.UpdateUserRequestDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAll(Pageable pageable);

    UserResponseDto update(String email, UpdateUserRequestDto requestDto);

    void remove(Long id);

    UserResponseDto findById(Long id);

    UserResponseDto updateOrganization(Long organizationId, String email);

    UserResponseDto getProfile(String email);

    List<UserResponseDto> getAllByOrganizationId(Long organizationId);

    List<UserResponseDto> getAllByCourseId(Long courseId);

    UserResponseDto updateCourse(Long courseId, String email);
}
