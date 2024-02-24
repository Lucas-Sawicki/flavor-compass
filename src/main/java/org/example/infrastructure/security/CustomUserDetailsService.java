package org.example.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email not found: " + email));

        Collection<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private UserDetails buildUserForAuthentication(UserEntity user, Collection<GrantedAuthority> authorities) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails userDetails = User
                .withUsername(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .build();

        return new User(
                userDetails.getUsername(),
                userDetails.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities);
    }


    private Collection<GrantedAuthority> getUserAuthority(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
