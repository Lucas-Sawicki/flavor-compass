package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrdersDAO;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrdersRepository implements OrdersDAO {
}
