package org.example.infrastructure.database.repository.jpa;

import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRangeJpaRepository extends JpaRepository<DeliveryRangeEntity, Long> {
    Optional<DeliveryRangeEntity> findByCityAndStreet(String city, String street);
    List<DeliveryRangeEntity> findByCity(String city);
    List<DeliveryRangeEntity> findByStreet(String street);
}
