package org.example.api.controller.rest;

import org.example.api.dto.LoginResponseDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthenticationController.BASE_PATH)
@CrossOrigin("*")
public class AuthenticationController {
    public static final String BASE_PATH = "/auth";
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RoleService roleService;
    @GetMapping(value = BASE_PATH)
    public String createOwnerPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public void registerUser(@RequestBody RegistrationDTO body){
        body.setRestApiUser(true);
        authenticationService.registerUser(body);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getEmail(), body.getPassword());
    }
}
