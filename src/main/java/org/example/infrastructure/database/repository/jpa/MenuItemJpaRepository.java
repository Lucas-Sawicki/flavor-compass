package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Integer> {
}
