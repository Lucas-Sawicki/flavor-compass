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
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class RegistrationController {
    public static final String REGISTER = "/registration";
    private UserService userService;
    private AuthenticationService authenticationService;


    @GetMapping(value = REGISTER)
    public String createOwnerPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "/registration";
    }
    @GetMapping("/success_register")
    public String successCreateAccount(Model model) {
        return "success_register.html";
    }

    @PostMapping(value = REGISTER)
    public ModelAndView register(@ModelAttribute RegistrationDTO registrationDTO,
                                           BindingResult bindingResult
    ) {
        if (userService.existsByEmail(registrationDTO.getEmail())) {
            return new ModelAndView("error", HttpStatus.BAD_REQUEST);
        }
        authenticationService.registerUser(registrationDTO);
        return new ModelAndView("redirect:/success_register");
    }
}

