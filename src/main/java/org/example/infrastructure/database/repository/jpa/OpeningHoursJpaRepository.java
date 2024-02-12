package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursJpaRepository extends JpaRepository<OpeningHoursEntity, Integer> {
}
