package org.example.api.controller;

import lombok.AllArgsConstructor;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.UserDTO;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.CustomerService;
import org.example.business.OwnerService;
import org.example.business.RoleService;
import org.example.business.UserService;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@AllArgsConstructor
public class RegistrationController {
public static final String REGISTER = "/registration";
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private OwnerService ownerService;
    private CustomerService customerService;
    private RoleService roleService;
    private UserMapper userMapper;



    @GetMapping(value = REGISTER)
    public String createOwnerPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("addressDTO", new AddressDTO());
        return "/registration";
    }

    @PostMapping(value = REGISTER)
    public ResponseEntity<String> register(@ModelAttribute RegistrationDTO registrationDTO,
                                           @ModelAttribute UserDTO userDTO,
                                           @ModelAttribute AddressDTO addressDTO,
                                           BindingResult bindingResult
                                           ){
        if (userService.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }
        if (addressDTO.isCustomer()) {

            Customer customer = userMapper.mapCustomer(registrationDTO, userDTO, addressDTO);
            String password = customer.getUser().getPassword();
            Role role = roleService.findByRole("CUSTOMER");
            User user = customer.getUser()
                    .withPassword("{bcrypt}" + passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            customerService.saveCustomer(customer.withUser(user));
        } else {
            Owner owner = userMapper.mapOwner(registrationDTO, userDTO);
            String password = owner.getUser().getPassword();
            Role role = roleService.findByRole("OWNER");
            User user = owner.getUser()
                    .withPassword("{bcrypt}" + passwordEncoder.encode(password))
                    .withRoles(Collections.singleton(role));
            ownerService.saveOwner(owner.withUser(user));
        }
        return ResponseEntity.ok("success_register");
    }
}

