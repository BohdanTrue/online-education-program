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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String CANNOT_FIND_ORGANIZATION_BY_ID = "Cannot find organization by id: ";
    private static final String CANNOT_FIND_COURSE_BY_ID = "Cannot find course by id: ";
    private static final String CANNOT_FIND_USERS_BY_ORGANIZATION_ID = "Cannot find users by organization id: ";
    private static final String CANNOT_FIND_USERS_BY_COURSE_ID = "Cannot find users by course id: ";
    private static final String CANNOT_FIND_USER_BY_ID = "Cannot find user by id: ";
    private static final String CANNOT_FIND_USER_BY_EMAIL = "Cannot find user by email: ";
    private static final String USER_ALREADY_EXISTS = "User: %s already exists on this course";
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final CourseRepository courseRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAll(Pageable pageable) {

        return userRepository.findAll(pageable).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto update(String email, UpdateUserRequestDto requestDto) {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_USER_BY_EMAIL));

        user.setFullName(requestDto.getFullName());
        user.setEmail(email);
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
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_ORGANIZATION_BY_ID + organizationId));

        User user = getByEmail(email);
        user.setOrganization(organization);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto updateCourse(Long courseId, String email) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_COURSE_BY_ID + courseId));

        User user = getByEmail(email);

        List<User> students = course.getStudents();

        if (students.contains(user)) {
            throw new RuntimeException(String.format(USER_ALREADY_EXISTS, user.getEmail()));
        }

        students.add(user);
        course.setStudents(students);
        courseRepository.save(course);

        user.getCourses().add(course);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getProfile(String email) {
        return userMapper.toDto(getByEmail(email));
    }

    @Override
    public List<UserResponseDto> getAllByOrganizationId(Long organizationId) {
        return userRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_USERS_BY_ORGANIZATION_ID + organizationId))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserResponseDto> getAllByCourseId(Long courseId) {
        return userRepository.findByCourseId(courseId)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_USERS_BY_COURSE_ID + courseId))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_USER_BY_ID + id));
    }

    private User getByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(CANNOT_FIND_USER_BY_EMAIL + email));
    }
}
