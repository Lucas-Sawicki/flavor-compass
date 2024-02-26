package org.example.api.controller.rest;

import lombok.AllArgsConstructor;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(AuthenticationRestController.BASE_PATH)
public class AuthenticationRestController {
    public static final String BASE_PATH = "/api/";
    public static final String REGISTER = "/registration";
    public static final String LOGIN = "/login";
    private final AuthenticationService authenticationService;



    @PostMapping(value = REGISTER)
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegistrationDTO body){
        body.setRestApiUser(true);
        return ResponseEntity.ok(authenticationService.registerUser(body));
    }

    @PostMapping(value = LOGIN)
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody LoginDTO body){
        return ResponseEntity.ok(authenticationService.loginUser(body));
    }

}
