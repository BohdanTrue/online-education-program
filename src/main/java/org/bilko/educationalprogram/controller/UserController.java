package org.bilko.educationalprogram.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.dto.user.UpdateUserRequestDto;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Get all registered users", description = "Get a list of all registered users")
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserResponseDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @Operation(
            summary = "Get all users by course id",
            description = "Get a list of all users who have a certain course"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/course/{courseId}")
    public List<UserResponseDto> getAllByCourseId(@PathVariable Long courseId) {
        return userService.getAllByCourseId(courseId);
    }

    @Operation(
            summary = "Get all users by organization id",
            description = "Get a list of all users who have a certain organization"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/organization/{organizationId}")
    public List<UserResponseDto> getAllByOrganizationId(@PathVariable Long organizationId) {
        return userService.getAllByOrganizationId(organizationId);
    }

    @Operation(
            summary = "Get an user information",
            description = "Get the information of the current user"
    )
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @GetMapping("/profile")
    public UserResponseDto getInfo(Authentication authentication) {
        String email = authentication.getName();

        return userService.getProfile(email);
    }

    @Operation(
            summary = "Get an user by id",
            description = "Get an user by certain id"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(
            summary = "Update an user",
            description = "Update an current user"
    )
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public UserResponseDto update(
            Authentication authentication,
            @RequestBody @Valid UpdateUserRequestDto requestDto
    ) {
        String email = authentication.getName();

        return userService.update(email, requestDto);
    }


    @Operation(
            summary = "Update an user organization",
            description = "User choose a certain organization by id"
    )
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/organization/{id}")
    public UserResponseDto chooseOrganization(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return userService.updateOrganization(id, email);
    }

    @Operation(
            summary = "Update an user course",
            description = "User choose a certain course by id"
    )
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/course/{id}")
    public UserResponseDto chooseCourse(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return userService.updateCourse(id, email);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.remove(id);
    }
}
