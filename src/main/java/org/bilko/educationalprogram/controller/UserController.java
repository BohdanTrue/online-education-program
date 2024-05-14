package org.bilko.educationalprogram.controller;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.user.UpdateUserRequestDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.model.User;
import org.bilko.educationalprogram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    ///api/users - GET: Отримати всіх користувачів
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    //api/users - GET: Отримати всіх користувачів by course
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/course/{courseId}")
//    public List<UserResponseDto> getAllByCourseId(@PathVariable Long courseId) {
//        return userService.getAllByCourseId(courseId);
//    }
    //api/users - GET: Отримати всіх користувачів by organization
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/organization/{organizationId}")
    public List<UserResponseDto> getAllByOrganizationId(@PathVariable Long organizationId) {
        return userService.getAllByOrganizationId(organizationId);
    }

    ///api/users/{id} - GET: Отримати користувача за його ID //admin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    ///api/users/{id} - PUT: Оновити існуючого користувача //admin
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        return userService.update(id, requestDto);
    }

    ///api/users/{id} - DELETE: Видалити користувача за його ID //admin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.remove(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/organization/{id}")
    public UserResponseDto chooseOrganization(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return userService.updateOrganization(id, email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/course/{id}")
    public UserResponseDto chooseCourse(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return userService.updateCourse(id, email);
    }

    @GetMapping("/profile")
    public UserResponseDto getInfo(Authentication authentication) {
        String email = authentication.getName();

        return userService.getProfile(email);
    }
}
