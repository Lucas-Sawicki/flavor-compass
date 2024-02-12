package org.example.api.dto.mapper;

import org.example.api.dto.OrdersDTO;
import org.example.domain.Orders;

public interface OrdersMapper extends OffsetDateTimeMapper {
   OrdersDTO map(final Orders orders);

}
