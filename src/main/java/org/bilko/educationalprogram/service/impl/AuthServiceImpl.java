package org.bilko.educationalprogram.service.impl;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.exception.RegistrationException;
import org.bilko.educationalprogram.exception.UnauthorizedActionException;
import org.bilko.educationalprogram.model.Role;
import org.bilko.educationalprogram.repository.RoleRepository;
import org.bilko.educationalprogram.security.CustomUserDetailsService;
import org.bilko.educationalprogram.security.JwtProvider;
import org.bilko.educationalprogram.dto.auth.AuthResponseDto;
import org.bilko.educationalprogram.dto.auth.LoginRequestDto;
import org.bilko.educationalprogram.dto.auth.RegisterRequestDto;
import org.bilko.educationalprogram.model.User;
import org.bilko.educationalprogram.repository.UserRepository;
import org.bilko.educationalprogram.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (userRepository.findFirstByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email: " + requestDto.getEmail() + " already exist");
        }

        User createdUser = new User();
        createdUser.setEmail(requestDto.getEmail());
        createdUser.setFullName(requestDto.getFullName());
        createdUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        createdUser.setRoles(Set.of(roleRepository.findRoleByRole(Role.RoleName.ROLE_STUDENT)));

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success!");

        return authResponse;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto requestDto) {
        String username = requestDto.getEmail();
        String password = requestDto.getPassword();

        Authentication authentication = authenticate(username, password);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success!");

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UnauthorizedActionException("Invalid email");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UnauthorizedActionException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }
}
