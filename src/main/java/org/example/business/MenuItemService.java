package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.mapper.MenuItemMapper;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.domain.Restaurant;
import org.example.domain.exception.CustomException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemDAO menuItemDAO;
    private final MenuItemMapper menuItemMapper;


    @Transactional
    public Optional<MenuItem> findMenuItemById(MenuItem menuItem) {
        try {
            MenuItem byId = menuItemDAO.findById(menuItem.getMenuItemId());
            return Optional.of(byId);
        } catch (DataAccessException ex) {
            throw new CustomException("Error accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public void addMenuItem(MenuItemDTO menuItemDTO, Restaurant restaurant) {
        try {
            MenuItem menuItem = menuItemMapper.map(menuItemDTO, restaurant);
            menuItemDAO.saveMenuItem(menuItem);
        } catch (DataAccessException ex) {
            throw new CustomException("Error saving menu item.", ex.getMessage());
        }
    }

    @Transactional
    public List<MenuItemDTO> findMenuByRestaurant(Integer id) {
        try {
            List<MenuItem> menu = menuItemDAO.findAllByRestaurantId(id);
            return menu.stream().map(menuItemMapper::map).toList();
        } catch (DataAccessException ex) {
            throw new CustomException("Error finding menu by restaurant.", ex.getMessage());
        }
    }

    @Transactional
    public Page<MenuItem> pagination(int page, int size, String sortBy, Integer id) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
            return menuItemDAO.findAll(pageRequest, id);
        } catch (DataAccessException ex) {
            throw new CustomException("Error paginating menu items.", ex.getMessage());
        }
    }

    @Transactional
    public MenuItem findById(Integer menuItemId) {
        try {
            return menuItemDAO.findById(menuItemId);
        } catch (DataAccessException ex) {
            throw new CustomException("Error finding menu item by id.", ex.getMessage());
        }
    }
}
