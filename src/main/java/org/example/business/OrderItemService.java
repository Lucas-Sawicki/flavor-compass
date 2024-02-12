package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrderItemDAO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemDAO orderItemDAO;

}
