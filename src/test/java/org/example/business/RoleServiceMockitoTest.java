package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import org.example.domain.Role;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.example.infrastructure.database.repository.mapper.RoleEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceMockitoTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void shouldFindRoleSuccessfully() {
        // given
        String role = "ROLE_OWNER";
        RoleEntity roleEntity = new RoleEntity();
        Role expectedRole = Role.builder().role("ROLE_OWNER").build();
        when(roleRepository.findByRole(role)).thenReturn(roleEntity);
        when(roleEntityMapper.mapFromEntity(roleEntity)).thenReturn(expectedRole);

        // when
        Role result = roleService.findByRole(role);

        // then
        assertThat("Role should be found", result, is(expectedRole));
        verify(roleRepository, times(1)).findByRole(role);
        verify(roleEntityMapper, times(1)).mapFromEntity(roleEntity);
    }

    @Test
    public void shouldThrowExceptionWhenTryFindRole() {
        // given
        String role = "ROLE_OWNER";
        when(roleRepository.findByRole(role)).thenReturn(null);

        // when, then
        assertThrows(EntityNotFoundException.class, () -> {
            roleService.findByRole(role);
        });
    }

}