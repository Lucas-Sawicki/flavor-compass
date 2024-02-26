package org.example.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.AuthenticationService;
import org.example.business.TokenService;
import org.example.business.UserService;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("exception", exception.getMessage());

        log.info("Can't log in: " + exception.getMessage());

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
        response.sendRedirect("/flavour-compass/error");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = tokenService.generateToken(userDetails);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            response.sendRedirect("/flavour-compass/");

        } catch (Exception e) {
            log.error("Error during authentication", e);
        }
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        String email = authentication.getName();
        Optional<Integer> userId = userService.findByEmail(email).map(UserEntity::getUserId);
        if (userId.isPresent()) {
            Integer id = userId.get();
            Map<String, String> roleTargetUrlMap = new HashMap<>();
            roleTargetUrlMap.put("OWNER", "/owner");
            roleTargetUrlMap.put("REST_API", "/api");
            roleTargetUrlMap.put("CUSTOMER", "/customer");
            final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (final GrantedAuthority grantedAuthority : authorities) {
                String authorityName = grantedAuthority.getAuthority();
                if (roleTargetUrlMap.containsKey(authorityName)) {
                    log.info(String.format("User logged with role: [%s]", authorityName));
                    return roleTargetUrlMap.get(authorityName);
                }
            }
        } else {
            throw new NotFoundException(String.format("user with id: [%s] not found", userId));
        }
        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
