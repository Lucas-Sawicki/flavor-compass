package org.example.api.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.TokenService;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    @Autowired
    private AuthenticationService authenticationService;

    private CustomUserDetailsService userDetailsService;
    private TokenService tokenService;

    @GetMapping(value = LOGIN)
    public String login(Model model){
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping(value = LOGIN)
    public ModelAndView processLogin(@ModelAttribute LoginDTO loginDTO, HttpServletResponse res) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        String token = tokenService.generateToken(userDetails);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);
        res.addCookie(cookie);
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("token", token);
        return mv;
    }

    @GetMapping(value = LOGOUT)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}

