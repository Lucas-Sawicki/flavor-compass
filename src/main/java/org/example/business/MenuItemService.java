package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.business.dao.MenuItemDAO;
import org.example.domain.MenuItem;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemDAO menuItemDAO;

    @Transactional
    public Optional<MenuItem> findMenuItem(MenuItem menuItem) {
        return menuItemDAO.findMenuItemByName(menuItem.getName());
    }

}
