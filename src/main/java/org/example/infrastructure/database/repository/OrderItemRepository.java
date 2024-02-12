package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrderItemDAO;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {
}
