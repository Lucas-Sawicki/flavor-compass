package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdersEntityMapper {
    @Mapping(target = "customer.address.restaurant", ignore = true)
    @Mapping(target = "customer.orders", ignore = true)
    @Mapping(target = "customer.opinions", ignore = true)
    @Mapping(target = "orderItems.order", ignore = true)
//    @Mapping(target = "restaurant.address.customer", ignore = true)
    Orders mapFromEntity(OrdersEntity entity);

//TODO
//    default Orders mapFromEntityWithCompletedStatus(OrdersEntity entity) {
//        return mapFromEntity(entity)
//                .withStatus("completed");
//    }

    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.orders", ignore = true)
    @Mapping(target = "customer.opinions", ignore = true)
    @Mapping(target = "orderItems.order", ignore = true)
    OrdersEntity mapToEntity(Orders orders);
}
