package org.example.api.controller.webMvcTests;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.example.api.controller.HomeController;
import org.example.business.TokenService;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class HomeControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenHome_thenReturnsHome() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void whenHomeWithTokenCookie_thenReturnsHomeWithToken() throws Exception {
        String token = "testToken";
        Cookie cookie = new Cookie("token", token);

        mockMvc.perform(get("/home").cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("token", token));
    }

}