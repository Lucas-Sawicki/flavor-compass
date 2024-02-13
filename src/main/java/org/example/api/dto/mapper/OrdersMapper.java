package org.example.api.dto.mapper;

import org.example.api.dto.OrdersDTO;
import org.example.domain.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends ObjectMapper {

   @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "mapOffsetDateTimeToString")
   @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerToString")
   @Mapping(source = "restaurant", target = "restaurant", qualifiedByName = "mapRestaurantToString")
   @Mapping(source = "menuItem", target = "menuItem", qualifiedByName = "mapMenuItemToString")
   OrdersDTO map(final Orders orders);

}
