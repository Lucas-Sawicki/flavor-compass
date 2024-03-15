package org.example.infrastructure.database.repository.jpa;

import org.example.api.dto.MenuItemDTO;
import org.example.domain.MenuItem;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Integer> {
    List<MenuItemEntity> findAllByRestaurantRestaurantId(Integer restaurantId);
    Page<MenuItemEntity> findAllByRestaurantRestaurantId(Integer restaurantId, Pageable pageable);
}
