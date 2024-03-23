package org.example.api.controller.mockitoTests;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.api.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeControllerMockitoTest {

    @InjectMocks
    HomeController homeController;

    @Mock
    HttpServletRequest request;

    @Mock
    Model model;

    @Mock
    Authentication authentication;

    @Test
    public void shouldThrowException() {
        //given
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setCookies(new Cookie("token", "12345"));
        //when
        when(request.getCookies()).thenReturn(req.getCookies());
        doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), any(Object.class));
        //then
        assertThrows(ResponseStatusException.class, () -> {
            homeController.home(request, model, authentication);
        });
    }

}
