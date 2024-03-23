package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.example.infrastructure.security.CustomUserDetails;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceMockitoTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;
    @Mock
    private UserEntityMapper userEntityMapper;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void shouldSuccessWithValidCredentials() {
        // given
        UserEntity userEntity = EntityFixtures.someUser1();
        String email = userEntity.getEmail();
        String password = userEntity.getPassword();
        RoleEntity role = RoleEntity.builder().role("ROLE_OWNER").build();
        userEntity.setRoles(Collections.singleton(role));
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        UserDetails userDetails = new CustomUserDetails(userEntity);
        String token = "token";
        User user = userEntityMapper.mapFromEntity(userEntity);

        // when
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userService.findEntityByEmail(email)).thenReturn(Optional.of(userEntity));
        when(tokenService.generateToken(userDetails)).thenReturn(token);

        // then
        AuthenticationResponseDTO response = authenticationService.loginUser(email, password);

        assertEquals(response.getToken(), token);
        assertEquals(response.getUser(), user);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userService, times(1)).findEntityByEmail(email);
        verify(tokenService, times(1)).generateToken(userDetails);
    }

    @Test
    public void shouldThrowExceptionWithInvalidCredentials() {
        // given
        String email = "test@test.com";
        String password = "wrongpassword";

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        // when
        Throwable exception = catchThrowable(() -> {
            authenticationService.loginUser(email, password);
        });

        // then
        assertThat(exception)
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Invalid login credentials.");
    }


    @Test
    public void shouldThrowExceptionForNonExistedUser() {
        // given
        String email = "nonexistent@test.com";
        String password = "password";
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

        // when
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userService.findEntityByEmail(email)).thenReturn(Optional.empty());

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.loginUser(email, password);
        });

        String expectedMessage = "User not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}