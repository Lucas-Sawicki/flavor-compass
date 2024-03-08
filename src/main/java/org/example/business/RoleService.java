package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.domain.Role;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.example.infrastructure.database.repository.mapper.RoleEntityMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Transactional
    public Role findByRole(String role) {
        RoleEntity entity = roleRepository.findByRole(role);
        if (entity == null) {
            throw new EntityNotFoundException("Role not found: " + role);
        }
        return roleEntityMapper.mapFromEntity(entity);
    }
}
