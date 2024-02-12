package org.example.infrastructure.database.repository.mapper;

import org.example.domain.OrderItem;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {

    @Mapping(target = "menuItem.orderItem", ignore = true)
    @Mapping(target = "order.orderItems", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity entity);

    @Mapping(target = "menuItem.orderItem", ignore = true)
    @Mapping(target = "order.orderItems", ignore = true)
    OrderItemEntity mapToEntity(OrderItem orderItem);

}
