package org.example.business.dao;

import org.example.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDAO {
    void save(OrderItem orderItem);

    List<OrderItem> findByOrderId(Integer orderId);
}
