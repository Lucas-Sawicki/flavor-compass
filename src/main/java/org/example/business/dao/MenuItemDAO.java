package org.example.business.dao;

import org.example.domain.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemDAO {
        Optional<MenuItem> findMenuItemByName(String name);
}
