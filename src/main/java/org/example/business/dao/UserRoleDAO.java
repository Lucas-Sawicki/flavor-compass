package org.example.business.dao;

import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.entity.UserRoleEntity;

public interface UserRoleDAO {

    void mergeUserAndRole(UserRoleEntity entity);

}
