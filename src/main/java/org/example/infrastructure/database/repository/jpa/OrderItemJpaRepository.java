package org.example.infrastructure.database.repository.jpa;

import org.example.domain.OrderItem;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Integer> {
    @Query("""
    SELECT oi FROM OrderItemEntity oi
    WHERE oi.order.orderId = :orderId
    """)
    List<OrderItemEntity> findByOrdersId(@Param("orderId")Integer orderId);
}
