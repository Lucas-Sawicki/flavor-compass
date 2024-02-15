package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.UserRoleDAO;
import org.example.infrastructure.database.entity.UserRoleEntity;
import org.example.infrastructure.database.repository.jpa.UserRoleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRoleRepository implements UserRoleDAO {

    private UserRoleJpaRepository userRoleJpaRepository;


    @Override
    public void mergeUserAndRole(UserRoleEntity entity) {
        userRoleJpaRepository.save(entity);
    }
}
