package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.example.infrastructure.database.repository.jpa.MenuItemJpaRepository;
import org.example.infrastructure.database.repository.mapper.MenuItemEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuItemRepository implements MenuItemDAO {

private final MenuItemJpaRepository menuItemJpaRepository;
private final MenuItemEntityMapper menuItemEntityMapper;

    @Override
    public Optional<MenuItem> findMenuItemByName(String name) {
        return menuItemJpaRepository.findMenuItemByName(name)
                .map(menuItemEntityMapper::mapFromEntity);
    }

    @Override
    public void saveMenuItem(MenuItem menuItem) {
        MenuItemEntity toSave = menuItemEntityMapper.mapToEntity(menuItem);
        menuItemJpaRepository.saveAndFlush(toSave);
    }


}
