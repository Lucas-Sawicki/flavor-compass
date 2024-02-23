package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrdersJpaRepository extends JpaRepository<OrdersEntity, Integer> {

    Set<OrdersEntity> findAllByStatus(String status);

    @Query("""
                SELECT o FROM OrdersEntity o
                WHERE o.customer.user.email = :email
            """)
    List<OrdersEntity> findOrdersByCustomerEmail(@Param("email") String email);

    @Query("""
                SELECT o FROM OrdersEntity o
                WHERE o.restaurant.email = :email
            """)
    List<OrdersEntity> findOrdersByRestaurantEmail(@Param("email") String email);
}
