package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersJpaRepository extends JpaRepository<OrdersEntity, Integer> {
}
