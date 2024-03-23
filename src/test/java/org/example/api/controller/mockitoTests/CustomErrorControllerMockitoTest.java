package org.example.api.controller.mockitoTests;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.api.controller.CustomErrorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomErrorControllerMockitoTest {

    @InjectMocks
    private CustomErrorController errorController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Model model;

    @Test
    public void shouldReturnErrorView() {
        // given
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn("Test error message");
        when(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE)).thenReturn(RuntimeException.class);

        // when
        String viewName = errorController.handleError(request, model);

        // then
        assertEquals("error", viewName);
        verify(model).addAttribute("errorMessage", "Test error message");
        verify(model).addAttribute("errorType", RuntimeException.class);
        verify(model).addAttribute("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

}
