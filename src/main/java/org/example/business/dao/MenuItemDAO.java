package org.example.business.dao;

import org.example.api.dto.MenuItemDTO;
import org.example.domain.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemDAO {
        Optional<MenuItem> findMenuItemByName(String name);

    void saveMenuItem(MenuItem menuItem);

    List<MenuItem> findAllByRestaurantId(Integer id);

    Page<MenuItem> findAll(PageRequest pageRequest, Integer id);

    MenuItem findById(Integer menuItemId);
}
