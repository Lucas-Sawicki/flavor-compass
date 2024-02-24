package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.LoginResponseDTO;
import org.example.business.AuthenticationService;
import org.example.business.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    public static final String LOGIN = "/login";
    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping(LOGIN)
    public String loginForm() {
        return "login";
    }

    @PostMapping(LOGIN)
    public LoginResponseDTO loginUser(@RequestParam @Valid String email, @RequestParam @Valid String password, HttpServletRequest request) {
        try {
            LoginResponseDTO response = authenticationService.loginUser(email, password);
            String clientIP = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            log.info("Logowanie udane dla użytkownika: " + email + ", IP klienta: " + clientIP + ", User-Agent: " + userAgent);

            return response;
        } catch (AuthenticationException e) {
            log.error("Błąd logowania dla użytkownika: " + email + ", wiadomość błędu: " + e.getMessage());
            throw e;
        }
    }
}