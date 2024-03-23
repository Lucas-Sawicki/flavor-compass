package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.AuthenticationController;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.TokenService;
import org.example.business.UserService;
import org.example.domain.Cart;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
public class AuthenticationControllerWebMvcTest {


    private final MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private SecurityContextHolder securityContextHolder;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        Authentication auth = new AnonymousAuthenticationToken("anonymous", "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenGetLoginThenReturnsLoginPage() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginDTO"))
                .andExpect(view().name("login"));
    }

    @Test
    public void whenGetRegisterThenReturnsRegistrationPage() throws Exception {
        mockMvc.perform(get("/auth/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registrationDTO"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void whenPostLoginWithValidDataThenRedirectsToHomePage() throws Exception {
        User expectedUser = OtherFixtures.someUser1();
        Owner owner = OtherFixtures.someOwner();
        User expectedOwner = expectedUser.withOwner(owner);
        AuthenticationResponseDTO dto = new AuthenticationResponseDTO();
        dto.setUser(expectedOwner);
        dto.setIsCustomer(false);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john@example.com");
        loginDTO.setPassword("password123");

        when(authenticationService.loginUser(loginDTO.getEmail(), loginDTO.getPassword())).thenReturn(dto);

        mockMvc.perform(post("/auth/login")
                        .flashAttr("loginDTO", loginDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(request().sessionAttribute("owner", dto.getUser().getOwner()));
    }
    @Test
    public void whenPostRegisterWithValidDataThenRedirectsToSuccessRegisterPage() throws Exception {
        RegistrationDTO registrationDTO = OtherFixtures.someRegistrationDTO();

        mockMvc.perform(post("/auth/registration")
                        .flashAttr("registrationDTO", registrationDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/success_register"));
    }
}

