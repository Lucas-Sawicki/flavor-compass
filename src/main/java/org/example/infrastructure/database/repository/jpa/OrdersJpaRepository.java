package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersJpaRepository extends JpaRepository<OrdersEntity, Integer> {

    Set<Orders> findAllByStatus(String status);
}
