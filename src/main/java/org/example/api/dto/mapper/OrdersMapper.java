package org.example.api.dto.mapper;

import org.example.api.dto.OrdersDTO;
import org.example.domain.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends ObjectMapper {

   @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "mapOffsetDateTimeToString")
   OrdersDTO map(final Orders orders);

}
