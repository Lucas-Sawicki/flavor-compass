package org.example.api.dto.mapper;

import org.example.api.dto.OrdersDTO;
import org.example.domain.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends ObjectMapper {

   @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "mapOffsetDateTimeToString")
   @Mapping(source = "deliveryTime", target = "deliveryTime", qualifiedByName = "mapDeliveryTimeToString")
   @Mapping(source = "customer", target = "customerId", qualifiedByName = "mapCustomer")
   @Mapping(source = "restaurant", target = "restaurantId", qualifiedByName = "mapRestaurant")
   OrdersDTO map(final Orders orders);

}
