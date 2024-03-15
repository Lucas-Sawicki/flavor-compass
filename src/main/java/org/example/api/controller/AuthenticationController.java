package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping(AuthenticationController.BASE_PATH)
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String BASE_PATH = "/auth";
    public static final String SUCCESS = "/success_register";
    public static final String REGISTER = "/registration";
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @GetMapping(value = LOGIN)
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping(value = LOGIN)
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, HttpSession session) {
        AuthenticationResponseDTO token = authenticationService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
        session.setAttribute("token", token);
        if (token.getIsCustomer()) {
            session.setAttribute("customer", token.getUser().getCustomer());
        } else {
            session.setAttribute("owner", token.getUser().getOwner());
        }
        return "home";
    }

    @GetMapping(value = LOGOUT)
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
    }

    @GetMapping(value = REGISTER)
    public String createOwnerPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "registration";
    }

    @GetMapping(value = SUCCESS)
    public String successCreateAccount(Model model) {
        return "success_register.html";
    }

    @PostMapping(value = REGISTER)
    public ModelAndView register(@Valid @ModelAttribute RegistrationDTO registrationDTO,
                                 BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }
        if (userService.existsByEmail(registrationDTO.getEmail())) {
            return new ModelAndView("error", HttpStatus.BAD_REQUEST);
        }
        authenticationService.registerUser(registrationDTO);
        return new ModelAndView("redirect:/auth/success_register");
    }

}

