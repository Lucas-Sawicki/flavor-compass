package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.example.infrastructure.database.repository.jpa.MenuItemJpaRepository;
import org.example.infrastructure.database.repository.mapper.MenuItemEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<MenuItem> findAllByRestaurantId(Integer id) {
        List<MenuItemEntity> entities = menuItemJpaRepository.findAllByRestaurantRestaurantId(id);
        return entities.stream().map(menuItemEntityMapper::mapFromEntity).toList();

    }

    @Override
    public Page<MenuItem> findAll(PageRequest pageRequest, Integer id) {
        Page<MenuItemEntity> menuItemEntities = menuItemJpaRepository.findAllByRestaurantRestaurantId(id, pageRequest);
        List<MenuItem> menuItems = menuItemEntities.stream()
                .map(menuItemEntityMapper::mapFromEntity)
                .toList();
        return new PageImpl<>(menuItems, pageRequest, menuItemEntities.getTotalElements());
    }

    @Override
    public MenuItem findById(Integer menuItemId) {
        Optional<MenuItemEntity> optionalEntity = menuItemJpaRepository.findById(menuItemId);
        if (optionalEntity.isPresent()) {
            MenuItemEntity menuItemEntity = optionalEntity.get();
            return menuItemEntityMapper.mapFromEntity(menuItemEntity);
        } else {
            throw new NotFoundException("Menu item not found");
        }
    }

}
