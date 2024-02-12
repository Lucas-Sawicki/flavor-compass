package org.example.infrastructure.database.repository.jpa;

import org.example.domain.MenuItem;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Integer> {
     Optional<MenuItemEntity> findMenuItemByName(String name);
}
