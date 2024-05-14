package org.bilko.educationalprogram.service;

import org.bilko.educationalprogram.dto.user.UpdateUserRequestDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.model.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAll();

    UserResponseDto update(Long id, UpdateUserRequestDto requestDto);

    void remove(Long id);

//    User getByEmail(String email);

    UserResponseDto findById(Long id);

    UserResponseDto updateOrganization(Long organizationId, String email);

//    List<UserResponseDto> getAllByCourseId(Long courseId);

    UserResponseDto getProfile(String email);

    List<UserResponseDto> getAllByOrganizationId(Long organizationId);

    UserResponseDto updateCourse(Long courseId, String email);
}
