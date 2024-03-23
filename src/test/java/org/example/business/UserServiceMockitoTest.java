package org.example.business;

import org.example.api.dto.rest.RestOwnerDTO;
import org.example.business.dao.UserDAO;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.util.EntityFixtures;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceMockitoTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldFindUserByEmail() {
        // given
        String email = "test@test.com";
        User user = OtherFixtures.someUser1();
        when(userDAO.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = userService.findByEmail(email);

        // then
        assertThat("User should be found", result.get(), is(user));
        verify(userDAO, times(1)).findByEmail(email);
    }

    @Test
    public void shouldFindUserEntityByEmail() {
        // given
        String email = "test@test.com";
        UserEntity userEntity = EntityFixtures.someUser1();
        when(userDAO.findEntityByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        Optional<UserEntity> result = userService.findEntityByEmail(email);

        // then
        assertThat("UserEntity should be found", result.get(), is(userEntity));
        verify(userDAO, times(1)).findEntityByEmail(email);
    }

    @Test
    public void shouldCheckExistsUserByEmailSuccessfully() {
        // given
        String email = "test@test.com";
        when(userDAO.existsByEmail(email)).thenReturn(true);

        // when
        Boolean exists = userService.existsByEmail(email);

        // then
        assertThat("Email should exist", exists, is(true));
        verify(userDAO, times(1)).existsByEmail(email);
    }

    @Test
    public void  shouldCreateUserCorrectly() {
        // given
        User user = OtherFixtures.someUser2();

        // when
        userService.createUser(user);

        // then
        verify(userDAO, times(1)).saveUser(user);
    }

    @Test
    public void shouldSetUserAsOwner() {
        // given
        User user = OtherFixtures.someUser3();
        User withRoles = user.withRoles(new HashSet<>());
        RestOwnerDTO body = new RestOwnerDTO();
        Role expectedRole = Role.builder().role("ROLE_OWNER").build();
        when(roleService.findByRole("ROLE_OWNER")).thenReturn(expectedRole);

        // when
        userService.setAsOwner(withRoles, body);

        // then
        assertTrue(withRoles.getRoles().contains(expectedRole));
        verify(userDAO, times(1)).setAsOwner(withRoles, body);
    }

    @Test
    public void shouldDeleteUserSuccessfully() {
        // given
        User user = OtherFixtures.someUser2();

        // when
        userService.deleteUser(user);

        // then
        verify(userDAO, times(1)).deleteUser(user);
    }

}