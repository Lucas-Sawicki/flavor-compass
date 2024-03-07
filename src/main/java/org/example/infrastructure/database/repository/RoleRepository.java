package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.RoleDAO;
import org.example.domain.Role;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.example.infrastructure.database.repository.mapper.RoleEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepository implements RoleDAO {
    private final RoleJpaRepository roleJpaRepository;


    @Override
    public RoleEntity findByRole(String role) {
        if (roleJpaRepository.findByRole(role).isPresent()) {
         return roleJpaRepository.findByRole(role).get();
        } else {
            throw new RuntimeException("Sorry, the specified role could not be found");
        }
    }
}
