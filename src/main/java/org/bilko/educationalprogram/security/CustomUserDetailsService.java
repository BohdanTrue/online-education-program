package org.bilko.educationalprogram.security;

import lombok.RequiredArgsConstructor;
import org.bilko.educationalprogram.model.User;
import org.bilko.educationalprogram.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String CANNOT_FIND_USER_BY_EMAIL = "Can't find user by email: ";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(CANNOT_FIND_USER_BY_EMAIL + email));
    }
}
