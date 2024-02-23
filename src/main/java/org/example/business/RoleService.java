package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.domain.Role;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
