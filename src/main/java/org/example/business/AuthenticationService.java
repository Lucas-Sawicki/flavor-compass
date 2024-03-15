package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.rest.RestRegistrationDTO;
import org.example.api.dto.mapper.UserMapper;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.example.infrastructure.security.CustomUserDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserEntityMapper userEntityMapper;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @Transactional
    public void registerUser(RegistrationDTO registrationDTO) {
        try {
            if (registrationDTO.getAddressDTO().isCustomer()) {
                Customer customer = userMapper.mapCustomer(registrationDTO, registrationDTO.getAddressDTO());
                String password = customer.getUser().getPassword();
                Role role = roleService.findByRole("ROLE_CUSTOMER");
                User user = customer.getUser()
                        .withPassword(passwordEncoder.encode(password))
                        .withRoles(Collections.singleton(role));
                customerService.createCustomer(customer.withUser(user));
            } else {
                Owner owner = userMapper.mapOwner(registrationDTO);
                String password = owner.getUser().getPassword();
                Role role = roleService.findByRole("ROLE_OWNER");
                User user = owner.getUser()
                        .withPassword(passwordEncoder.encode(password))
                        .withRoles(Collections.singleton(role));
                ownerService.createOwner(owner.withUser(user));
            }
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException("User can't be created", ex.getMessage());
        }
    }

    @Transactional
    public void registerApiUser(RestRegistrationDTO registrationDTO) {
        try {
            if (registrationDTO.isRestApiUser()) {
                User user = userMapper.mapApiUser(registrationDTO);
                String password = user.getPassword();
                Role rest_api = roleService.findByRole("ROLE_REST_API");
                userService.createUser(user
                        .withPassword(passwordEncoder.encode(password))
                        .withRoles(Collections.singleton(rest_api)));
            }
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException("User can't be created", ex.getMessage());
        }
    }

    @Transactional
    public AuthenticationResponseDTO loginUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userEntity = userService.findEntityByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("User not found!"));
            UserDetails userDetails = new CustomUserDetails(userEntity);
            var jwtToken = tokenService.generateToken(userDetails);
            User user = userEntityMapper.mapFromEntity(userEntity);
            AuthenticationResponseDTO responseDTO = AuthenticationResponseDTO.builder()
                    .user(user)
                    .token(jwtToken)
                    .build();

            if (userEntity.getCustomer() != null) {
                responseDTO.setIsCustomer(true);
            }
            return responseDTO;
        } catch (AuthenticationException ex) {
            throw new CustomException("Invalid login credentials.", ex.getMessage());
        }
    }

}
