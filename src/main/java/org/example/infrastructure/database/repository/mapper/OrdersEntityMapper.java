package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdersEntityMapper {

    @Mapping(target = "user.roles", ignore = true)
    @Mapping(target = "customer.opinions", ignore = true)
    @Mapping(target = "restaurant.address.customer.user", ignore = true)
    @Mapping(target = "restaurant.owner", ignore = true)
    @Mapping(target = "restaurant.opinions", ignore = true)
    @Mapping(target = "menuItem.opinions", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Orders mapFromEntity(OrdersEntity entity);


    @Mapping(target = "user.roles", ignore = true)
    @Mapping(target = "customer.opinions", ignore = true)
    @Mapping(target = "restaurant.address.customer.user", ignore = true)
    @Mapping(target = "restaurant.owner", ignore = true)
    @Mapping(target = "restaurant.opinions", ignore = true)
    @Mapping(target = "menuItem.opinions", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    OrdersEntity mapToEntity(Orders orders);
}
