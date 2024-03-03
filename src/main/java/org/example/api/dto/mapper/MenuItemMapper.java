package org.example.api.dto.mapper;

import org.example.api.dto.MenuItemDTO;
import org.example.domain.MenuItem;
import org.example.domain.Restaurant;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    default MenuItem map(MenuItemDTO menuItemDTO, Restaurant restaurant) {
        MenuItem menuItem = MenuItem.builder()
                .name(menuItemDTO.getName())
                .category(menuItemDTO.getCategory())
                .description(menuItemDTO.getDescription())
                .price(new BigDecimal(menuItemDTO.getPrice()))
                .photoUrl(menuItemDTO.getPhotoUrl())
                .build();

        return menuItem.withRestaurant(restaurant);
    }
    default MenuItemDTO map(MenuItem menuItem) {

        return MenuItemDTO.builder()
                .id(menuItem.getMenuItemId().toString())
                .name(menuItem.getName())
                .category(menuItem.getCategory())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice().toString())
                .photoUrl(menuItem.getPhotoUrl())
                .restaurantsList(menuItem.getRestaurant().getLocalName())
                .build();
    }
}
