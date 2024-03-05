package org.example.infrastructure.database.repository.mapper;

import org.example.domain.OpeningHours;
import org.example.domain.Opinion;
import org.example.domain.OrderItem;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdersEntityMapper {
    @Mapping(target = "customer.orders", ignore = true)
    @Mapping(target = "customer.user", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "restaurant.orders", ignore = true)
    @Mapping(target = "restaurant.owner", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.openingHours", ignore = true)
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapFromOrderItems")
    Orders mapFromEntity(OrdersEntity entity);
    @Mapping(target = "restaurant.orders", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapToOrderItems")
    OrdersEntity mapToEntity(Orders orders);

    @Named("mapFromOrderItems")
    default List<OrderItem> mapFromOrderItems(List<OrderItemEntity> orderItems) {
        return orderItems.stream()
                .map(this::mapFromEntity)
                .toList();
    }
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "menuItem.orderItems", ignore = true)
    @Mapping(target = "menuItem.restaurant", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity entity);

    @Named("mapToOrderItems")
    default List<OrderItemEntity> mapToOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapToEntity)
                .toList();

    }
    @Mapping(target = "order", ignore = true)
    OrderItemEntity mapToEntity(OrderItem entity);
}
