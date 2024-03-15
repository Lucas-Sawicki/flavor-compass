package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Orders;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrdersJpaRepository extends JpaRepository<OrdersEntity, Integer> {

    @Query("SELECT o FROM OrdersEntity o WHERE o.orderNumber = :orderNumber")
    Optional<OrdersEntity> findByOrderNumber(@Param("orderNumber")Long orderNumber);

    void deleteByOrderNumber(Long orderNumber);
    @Query("SELECT o FROM OrdersEntity o LEFT JOIN FETCH o.orderItems WHERE o.customer = :customer ORDER BY o.orderDate DESC")
    List<OrdersEntity> findByCustomer(@Param("customer") CustomerEntity customer);
    @Query("SELECT o FROM OrdersEntity o JOIN o.restaurant r WHERE r.owner = :owner ORDER BY o.orderDate DESC")
    List<OrdersEntity> findByOwner(@Param("owner") OwnerEntity ownerEntity);
}
