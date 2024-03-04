package org.example.api.controller.rest;

import lombok.AllArgsConstructor;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.example.domain.Owner;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;



    @PostMapping(value = REGISTER)
    public ResponseEntity<String> register(@RequestBody RegistrationDTO body){
        if (userService.existsByEmail(body.getEmail())){
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        body.setRestApiUser(true);
        authenticationService.registerUser(body);
        return ResponseEntity.ok("User registered success");
    }

    @PostMapping(value = LOGIN)
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody LoginDTO body){
        return ResponseEntity.ok(authenticationService.loginUser(body.getEmail(),body.getPassword()));
    }

}
