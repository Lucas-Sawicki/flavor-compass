package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrdersDAO;
import org.example.domain.Orders;
import org.example.infrastructure.database.repository.jpa.OrdersJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrdersRepository implements OrdersDAO {

    private final OrdersJpaRepository ordersJpaRepository;


    @Override
    public List<Orders> findPendingOrders() {
        return ordersJpaRepository.findAllByStatus("pending").stream().toList();
    }
}