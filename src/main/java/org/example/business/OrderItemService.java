package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.OrderItemDAO;
import org.example.domain.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemDAO orderItemDAO;

    @Transactional
    public void save(OrderItem orderItem) {
        orderItemDAO.save(orderItem);
    }

    public List<OrderItem> findByOrderId(Integer orderId) {
        return orderItemDAO.findByOrderId(orderId);
    }
}
