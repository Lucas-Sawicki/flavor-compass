package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
