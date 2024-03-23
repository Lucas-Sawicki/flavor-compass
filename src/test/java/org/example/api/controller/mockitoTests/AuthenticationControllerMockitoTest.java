package org.example.api.controller.mockitoTests;

import org.example.api.controller.AuthenticationController;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.example.domain.User;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerMockitoTest {
    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    UserService userService;


    @Test
    public void whenLoginShouldReturnsHomePage() {
        //given
        LoginDTO loginDTO = OtherFixtures.someLoginExample();

        User user = OtherFixtures.someUser1();

        AuthenticationResponseDTO token = new AuthenticationResponseDTO();
        token.setToken("token");
        token.setIsCustomer(true);
        token.setUser(user);

        //when
        when(authenticationService.loginUser(loginDTO.getEmail(), loginDTO.getPassword())).thenReturn(token);
        String viewName = authenticationController.login(loginDTO, new MockHttpSession());

        //then
        assertEquals("home", viewName);
        verify(authenticationService, times(1)).loginUser(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @Test
    public void whenGetLoginThenReturnsLoginPage() {
        //given
        Model model = mock(Model.class);
        //when
        String viewName = authenticationController.login(model);

        //then
        assertEquals("login", viewName);
        verify(model, times(1)).addAttribute(eq("loginDTO"), any(LoginDTO.class));
    }
    @Test
    public void whenGetLogoutThenReturnsHomePage() {
        //given, when
        String viewName = authenticationController.logout(new MockHttpSession());
        //then
        assertEquals("home", viewName);
    }

    @Test
    public void whenGetRegisterThenReturnsRegistrationPage() {
        //given
        Model model = mock(Model.class);
        //when
        String viewName = authenticationController.createOwnerPage(model);
        //then
        assertEquals("registration", viewName);
        verify(model, times(1)).addAttribute(eq("registrationDTO"), any(RegistrationDTO.class));
    }

    @Test
    public void whenGetSuccessThenReturnsSuccessRegisterPage() {
        //given
        Model model = mock(Model.class);
        //when
        String viewName = authenticationController.successCreateAccount(model);
        //then
        assertEquals("success_register.html", viewName);
    }

    @Test
    public void whenRegisterWithExistingEmailThenReturnsErrorView() {
        //given
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("test@test.com");
        registrationDTO.setPassword("password");
        BindingResult bindingResult = mock(BindingResult.class);
        //when
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail(registrationDTO.getEmail())).thenReturn(true);

        ModelAndView modelAndView = authenticationController.register(registrationDTO, bindingResult);
        //then
        assertEquals("error", modelAndView.getViewName());
        assertEquals(HttpStatus.BAD_REQUEST, modelAndView.getStatus());
    }
    @Test
    public void whenRegisterWithValidDataThenReturnsSuccessRegisterView() {
        //given
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("test@test.com");
        registrationDTO.setPassword("password");
        BindingResult bindingResult = mock(BindingResult.class);
        //when
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail(registrationDTO.getEmail())).thenReturn(false);
        ModelAndView modelAndView = authenticationController.register(registrationDTO, bindingResult);
        //then
        assertEquals("redirect:/auth/success_register", modelAndView.getViewName());
    }
}