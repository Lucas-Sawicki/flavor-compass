package org.example.business;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.mapper.UserMapper;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthenticationResponseDTO registerUser(RegistrationDTO registrationDTO) {

        if (registrationDTO.isRestApiUser()) {
            User user = userMapper.mapApiUser(registrationDTO);
            String password = user.getPassword();
            Role rest_api = roleService.findByRole("REST_API");
            userService.createUser(user
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(rest_api)));
            Optional<UserEntity> userEntity = userService.findByEmail(user.getEmail());
            var jwtToken = tokenService.generateToken(userEntity.get());
            return AuthenticationResponseDTO.builder()
                    .token(jwtToken)
                    .build();
        } else if (registrationDTO.getAddressDTO().isCustomer()) {
            Customer customer = userMapper.mapCustomer(registrationDTO, registrationDTO.getAddressDTO());
            String password = customer.getUser().getPassword();
            Role role = roleService.findByRole("CUSTOMER");
            User user = customer.getUser()
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            customerService.createCustomer(customer.withUser(user));
            Optional<UserEntity> entity = userService.findByEmail(user.getEmail());
            var jwtToken = tokenService.generateToken(entity.get());
            return AuthenticationResponseDTO.builder()
                    .token(jwtToken)
                    .build();
        } else {
            Owner owner = userMapper.mapOwner(registrationDTO);
            String password = owner.getUser().getPassword();
            Role role = roleService.findByRole("OWNER");
            User user = owner.getUser()
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            ownerService.saveOwner(owner.withUser(user));
            Optional<UserEntity> entity = userService.findByEmail(user.getEmail());
            var jwtToken = tokenService.generateToken(entity.get());
            return AuthenticationResponseDTO.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    public AuthenticationResponseDTO loginUser(LoginDTO loginDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            var user = userService.findByEmail(loginDTO.getEmail()).orElseThrow();

            var jwtToken = tokenService.generateToken(user);
            return AuthenticationResponseDTO.builder()
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            throw new AuthenticationException("Bad password", e) {
            };
        }
    }
}
