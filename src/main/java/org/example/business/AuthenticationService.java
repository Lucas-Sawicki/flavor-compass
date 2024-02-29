package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.mapper.UserMapper;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

    public void registerUser(RegistrationDTO registrationDTO) {

        if (registrationDTO.isRestApiUser()) {
            User user = userMapper.mapApiUser(registrationDTO);
            String password = user.getPassword();
            Role rest_api = roleService.findByRole("REST_API");
            userService.createUser(user
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(rest_api)));
        } else if (registrationDTO.getAddressDTO().isCustomer()) {
            Customer customer = userMapper.mapCustomer(registrationDTO, registrationDTO.getAddressDTO());
            String password = customer.getUser().getPassword();
            Role role = roleService.findByRole("CUSTOMER");
            User user = customer.getUser()
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            customerService.createCustomer(customer.withUser(user));
        } else {
            Owner owner = userMapper.mapOwner(registrationDTO);
            String password = owner.getUser().getPassword();
            Role role = roleService.findByRole("OWNER");
            User user = owner.getUser()
                    .withPassword(passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            ownerService.createOwner(owner.withUser(user));
        }
    }

    public AuthenticationResponseDTO loginUser(LoginDTO loginDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userService.findEntityByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("User not found!"));
            UserDetails userDetails = new CustomUserDetails(user);
            var jwtToken = tokenService.generateToken(userDetails);
            return AuthenticationResponseDTO.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Something goes wrong!", e) {
            };
        }
    }
}
