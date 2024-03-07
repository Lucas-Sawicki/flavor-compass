package org.example.business.dao;

import org.example.domain.DeliveryRange;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliverRangeDAO {


    void saveDeliverRange(DeliveryRange range);

    List<DeliveryRange> findByStreet(String street);

    List<DeliveryRange> findByCity(String city);

    Optional<DeliveryRangeEntity> findByCityAndStreet(String city, String street);
}
