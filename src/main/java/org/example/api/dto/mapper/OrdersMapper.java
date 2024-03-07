package org.example.api.dto.mapper;

import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.OrdersDTO;
import org.example.domain.OrderItem;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends ObjectMapper {

    @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "deliveryTime", target = "deliveryTime", qualifiedByName = "mapDeliveryTimeToString")
    @Mapping(source = "customer", target = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(source = "restaurant", target = "restaurantId", qualifiedByName = "mapRestaurant")
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapOrderItem")
    OrdersDTO map(final Orders orders);

    @Named("mapOrderItem")
    default List<OrderItemDTO> mapOrderItem(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapFromDomain)
                .collect(Collectors.toList());
    }

    default OrderItemDTO mapFromDomain(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setMenuItemId(orderItem.getMenuItem().getMenuItemId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }
}