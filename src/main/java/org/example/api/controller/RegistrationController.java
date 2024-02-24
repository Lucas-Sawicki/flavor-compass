package org.example.api.controller;

import lombok.AllArgsConstructor;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {
    public static final String REGISTER = "/registration";
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping(value = REGISTER)
    public String createOwnerPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "/registration";
    }

    @PostMapping(value = REGISTER)
    public ResponseEntity<String> register(@ModelAttribute RegistrationDTO registrationDTO,
                                           BindingResult bindingResult
    ) {
        if (userService.existsByEmail(registrationDTO.getEmail())) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }
        authenticationService.registerUser(registrationDTO);
        return ResponseEntity.ok("success_register");
    }


}

