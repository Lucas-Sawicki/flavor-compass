package org.example.business.dao;

import org.example.domain.Role;
import org.example.infrastructure.database.entity.RoleEntity;

public interface RoleDAO {
    Role saveRole(Role role);
//    Role findByRole(String role);
    RoleEntity findByRole(String role);
}
