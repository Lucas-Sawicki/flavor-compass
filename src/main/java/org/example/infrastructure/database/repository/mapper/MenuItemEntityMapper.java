package org.example.infrastructure.database.repository.mapper;

import org.example.domain.MenuItem;
import org.example.domain.OrderItem;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemEntityMapper {

    @Mapping(target = "menuItem.orderItem", ignore = true)
    @Mapping(target = "orders.", ignore = true)
    @Mapping(target = "orderItem.order", ignore = true)
    @Mapping(target = "opinions", ignore = true)
    MenuItem mapFromEntity(MenuItemEntity entity);

    @Mapping(target = "menuItem.orderItem", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "orderItem.order", ignore = true)
    @Mapping(target = "opinions", ignore = true)
    MenuItemEntity mapToEntity(MenuItem menuItem);
}
