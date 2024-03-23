package org.example.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Account Management")
public class AuthenticationRestController {
    public static final String BASE_PATH = "/api";
    public static final String REGISTER = "/registration";
    public static final String LOGIN = "/login";
    public static final String BECOME_OWNER = "/owner/create";
    public static final String DELETE = "/delete";
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(
            summary ="Create a REST API account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestRegistrationDTO.class),
                            examples = @ExampleObject(
                                    name = "register",
                                    value = """
                                            {
                                               "email": "example@example.com",
                                                "password": "12345678"
                                            }""" )) ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registration was successful"
                    )
            }
    )
    @PostMapping(value = REGISTER)
    public ResponseEntity<String> register(@Valid @RequestBody RestRegistrationDTO body) {
        if (userService.existsByEmail(body.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        body.setRestApiUser(true);
        authenticationService.registerApiUser(body);
        return ResponseEntity.ok("User registered success");
    }
    @Operation(
            summary ="Log in to get a token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class),
                            examples = @ExampleObject(
                                    name = "login",
                                    value = """
                                            {
                                               "email": "example@example.com",
                                                "password": "12345678"
                                            }""" )) ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login success"
                    )
            }
    )

    @PostMapping(value = LOGIN)
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody LoginDTO body) {
        return ResponseEntity.ok(authenticationService.loginUser(body.getEmail(), body.getPassword()));
    }
    @Operation(
            summary ="Upgrade your account to the restaurant owner level",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestOwnerDTO.class),
                            examples = @ExampleObject(
                                    name = "updateUserAsOwner",
                                    value = """
                                            {
                                                "name": "Name",
                                                "surname": "Surname",
                                                "phone": "+00 000 000 000"
                                            }""" )) ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Become restaurant owner successfully"
                    )
            }
    )
    @SecurityRequirement(name = "BearerAuth")
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

    @Operation(
            summary ="Endpoint to delete your account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class),
                            examples = @ExampleObject(
                                    name = "deleteAccount",
                                    value = """
                                            {
                                               "email": "example@example.com",
                                                "password": "12345678"
                                            }""" )) ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User account successfully deleted"
                    )
            }
    )
    @SecurityRequirement(name = "BearerAuth")
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
