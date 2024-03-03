package org.example.infrastructure.database.repository.mapper;

import org.example.domain.MenuItem;
import org.example.domain.Opinion;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemEntityMapper {

    @Mapping( target = "opinions", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.openingHours", ignore = true)
    @Mapping(target = "restaurant.owner", ignore = true)
    MenuItem mapFromEntity(MenuItemEntity entity);
    @Mapping( target = "opinions", ignore = true)
    MenuItemEntity mapToEntity(MenuItem menuItem);


}
