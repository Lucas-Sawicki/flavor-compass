package org.example.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceMockitoTest {
    @InjectMocks
    private TokenService tokenService;

    @Test
    public void shouldGenerateTokenCorrectly() {
        // given
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());

        // when
        String token = tokenService.generateToken(userDetails);

        // then
        assertNotNull(token);
    }

    @Test
    public void shouldValidateTokenCorrectly() {
        // given
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());
        String token = tokenService.generateToken(userDetails);

        // when
        Boolean isValid = tokenService.validateToken(token, userDetails);

        // then
        assertTrue(isValid);
    }

    @Test
    public void shouldExtractUserEmail() {
        // given
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@test.com");
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());
        String token = tokenService.generateToken(userDetails);

        // when
        String email = tokenService.extractUserEmail(token);

        // then
        assertEquals("test@test.com", email);
    }
}