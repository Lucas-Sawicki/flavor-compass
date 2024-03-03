package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.mapper.MenuItemMapper;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.example.domain.Restaurant;
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
        MenuItem byId = menuItemDAO.findById(menuItem.getMenuItemId());
        return Optional.of(byId);

    }
    @Transactional
    public void addMenuItem(MenuItemDTO menuItemDTO, Restaurant restaurant) {
        MenuItem menuItem = menuItemMapper.map(menuItemDTO, restaurant);
        menuItemDAO.saveMenuItem(menuItem);
    }

    public List<MenuItemDTO> findMenuByRestaurant(Integer id) {
        List<MenuItem> menu = menuItemDAO.findAllByRestaurantId(id);
        return menu.stream().map(menuItemMapper::map).toList();
    }

    public Page<MenuItem> pagination(int page, int size, String sortBy, Integer id) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return menuItemDAO.findAll(pageRequest, id);
    }

    public MenuItem findById(Integer menuItemId) {
        return  menuItemDAO.findById(menuItemId);
    }
}
