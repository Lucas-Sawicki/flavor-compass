package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, Integer> {
}
