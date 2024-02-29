package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.mapper.MenuItemMapper;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.domain.Restaurant;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemDAO menuItemDAO;
    private final MenuItemMapper menuItemMapper;


    @Transactional
    public Optional<MenuItem> findMenuItem(MenuItem menuItem) {
        return menuItemDAO.findMenuItemByName(menuItem.getName());
    }
    @Transactional
    public void addMenuItem(MenuItemDTO menuItemDTO, Restaurant restaurant) {
        MenuItem menuItem = menuItemMapper.map(menuItemDTO, restaurant);
        menuItemDAO.saveMenuItem(menuItem);
    }
}
