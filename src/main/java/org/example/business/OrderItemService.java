package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.OrderItemDAO;
import org.example.domain.OrderItem;
import org.example.domain.exception.CustomException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemDAO orderItemDAO;

    @Transactional
    public void save(OrderItem orderItem) {
        try {
            orderItemDAO.save(orderItem);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while saving order item.", ex.getMessage());
        }
    }
}
