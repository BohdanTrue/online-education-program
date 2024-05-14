package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.user.UpdateUserRequestDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.mapper.UserMapper;
import org.bilko.educationalprogram.model.Course;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.model.User;
import org.bilko.educationalprogram.repository.CourseRepository;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.repository.UserRepository;
import org.bilko.educationalprogram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final CourseRepository courseRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAll() {

        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserRequestDto requestDto) {
        User user = getById(id);

        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());

        return userMapper.toDto(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(getById(id));
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userMapper.toDto(getById(id));
    }

    @Override
    @Transactional
    public UserResponseDto updateOrganization(Long organizationId, String email) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find organization by id: " + organizationId));

        User user = getByEmail(email);
        user.setOrganization(organization);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto updateCourse(Long courseId, String email) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find course by id: " + courseId));

        User user = getByEmail(email);

        List<User> students = course.getStudents();

        if (students.contains(user)) {
            throw new RuntimeException("User already exist: " + user.getEmail());
        }

        students.add(user);
        course.setStudents(students);
        courseRepository.save(course);

        user.getCourses().add(course);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

//    @Override
//    public List<UserResponseDto> getAllByCourseId(Long courseId) {
//        List<User> users = userRepository.findByCourseId(courseId)
//                .orElseThrow(() -> new EntityNotFoundException("Cannot find users by course id: " + courseId));
//
//        return users.stream()
//                .map(userMapper::toDto)
//                .toList();
//    }

    @Override
    public UserResponseDto getProfile(String email) {
        return userMapper.toDto(getByEmail(email));
    }

    @Override
    public List<UserResponseDto> getAllByOrganizationId(Long organizationId) {
        List<User> users = userRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find users by organization id: " + organizationId));

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user by id: " + id));
    }

    private User getByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user by email: " + email));
    }
}
