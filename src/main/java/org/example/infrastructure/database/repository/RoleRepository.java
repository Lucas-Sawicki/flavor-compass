package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.RoleDAO;
import org.example.domain.Role;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.example.infrastructure.database.repository.mapper.RoleEntityMapper;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RoleRepository implements RoleDAO {

    private final RoleEntityMapper roleEntityMapper;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public void saveRole(Role role) {
        RoleEntity toSave = roleEntityMapper.mapToEntity(role);
        RoleEntity saved = roleJpaRepository.saveAndFlush(toSave);
        roleEntityMapper.mapFromEntity(saved);
    }
}
