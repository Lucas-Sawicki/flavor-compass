package org.example.business.dao;

import org.example.infrastructure.database.entity.RoleEntity;

public interface RoleDAO {
    RoleEntity findByRole(String role);
}
