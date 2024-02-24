package org.example.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.LoginResponseDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.mapper.UserMapper;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.springframework.security.core.AuthenticationException;
import org.example.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private RoleJpaRepository roleJpaRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    public void registerUser(RegistrationDTO registrationDTO) {

         if (registrationDTO.isRestApiUser()) {
            User user = userMapper.mapApiUser(registrationDTO);
            String password = user.getPassword();
            Role rest_api = roleService.findByRole("REST_API");
            userService.createUser(user.withPassword(passwordEncoder.encode(password)).withRoles(Collections.singleton(rest_api)));
         }
       else if (registrationDTO.getAddressDTO().isCustomer()) {
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
            ownerService.saveOwner(owner.withUser(user));
        }
    }

    public LoginResponseDTO loginUser(String email, String password) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            String token = tokenService.generateJwt(authentication);

            return new LoginResponseDTO(userJpaRepository.findByEmail(email).get(), token);

        } catch (AuthenticationException e) {
            throw new AuthenticationException("Something went wrong during authentication", e) {
            };
        }
    }
}
