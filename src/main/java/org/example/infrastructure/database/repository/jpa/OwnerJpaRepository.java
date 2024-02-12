package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Integer> {
}
