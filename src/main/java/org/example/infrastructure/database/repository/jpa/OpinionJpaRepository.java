package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.OpinionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionJpaRepository extends JpaRepository<OpinionEntity, Integer> {
}
