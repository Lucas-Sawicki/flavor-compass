package org.example.api.controller.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.*;
import org.example.api.dto.rest.RestOwnerDTO;
import org.example.api.dto.rest.RestRegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.example.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(AuthenticationRestController.BASE_PATH)
@Validated
public class AuthenticationRestController {
    public static final String BASE_PATH = "/api/";
    public static final String REGISTER = "/registration";
    public static final String LOGIN = "/login";
    public static final String BECOME_OWNER = "/owner/create";
    private static final String DELETE = "/delete";
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping(value = REGISTER)
    public ResponseEntity<String> register(@Valid @RequestBody RestRegistrationDTO body) {
        if (userService.existsByEmail(body.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        body.setRestApiUser(true);
        authenticationService.registerApiUser(body);
        return ResponseEntity.ok("User registered success");
    }

    @PostMapping(value = LOGIN)
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody LoginDTO body) {
        return ResponseEntity.ok(authenticationService.loginUser(body.getEmail(), body.getPassword()));
    }

    @PatchMapping(value = BECOME_OWNER)
    public ResponseEntity<String> updateUserAsOwner(
            Principal principal,
            @Valid @RequestBody RestOwnerDTO body
    ) {
        String email = principal.getName();

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found"
                ));
        if (user.getOwner() != null) {
            return ResponseEntity.ok("User is already a restaurant owner");
        }
        userService.setAsOwner(user, body);
        return ResponseEntity.ok(String.format("User [%s] become restaurant owner successfully", user));
    }

    @DeleteMapping(value = DELETE)
    public ResponseEntity<String> deleteAccount(@RequestBody LoginDTO loginDTO, HttpServletRequest request, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Can't find user with email: [%s]", loginDTO.getEmail())));
        String emailToMatch = user.getEmail();
        if (!email.equals(emailToMatch)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sorry this isn't yor account");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        log.info("User deletion attempt: email=" + loginDTO.getEmail() + ", timestamp=" + LocalDateTime.now() + ", IP=" + request.getRemoteAddr());
        userService.deleteUser(user);
        return ResponseEntity.ok("User account successfully deleted");
    }

}
