package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.DeliverRangeDAO;
import org.example.domain.DeliveryRange;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.DeliveryRangeJpaRepository;
import org.example.infrastructure.database.repository.mapper.DeliveryRangeEntityMapper;

import org.example.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeliveryRangeRepository implements DeliverRangeDAO {

    private final DeliveryRangeJpaRepository deliveryRangeJpaRepository;
    private final DeliveryRangeEntityMapper deliveryRangeMapper;
    private final RestaurantEntityMapper restaurantMapper;

    @Override
    public void saveDeliverRange(DeliveryRange range) {
        String city = range.getCity();
        String street = range.getStreet();
        List<Restaurant> restaurants = range.getRestaurants();
        Optional<DeliveryRangeEntity> existingDeliveryRange = findByCityAndStreet(city, street);
        DeliveryRangeEntity entity = deliveryRangeMapper.mapToEntity(range);
        if (existingDeliveryRange.isPresent()) {
            List<RestaurantEntity> newRestaurantsList = new ArrayList<>(existingDeliveryRange.get().getRestaurants());
            newRestaurantsList.addAll(restaurants.stream().map(restaurantMapper::mapToEntity).toList());
            existingDeliveryRange.get().setRestaurants(newRestaurantsList);
            entity = existingDeliveryRange.get();
        }
        deliveryRangeJpaRepository.save(entity);
    }

    @Override
    public List<DeliveryRange> findByStreet(String street) {
        List<DeliveryRangeEntity> entities = deliveryRangeJpaRepository.findByStreet(street);
        return entities.stream()
                .map(deliveryRangeMapper::mapFromEntity)
                .toList();

    }

    @Override
    public List<DeliveryRange> findByCity(String city) {
        List<DeliveryRangeEntity> entities = deliveryRangeJpaRepository.findByCity(city);
        return entities.stream()
                .map(deliveryRangeMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<DeliveryRangeEntity> findByCityAndStreet(String city, String street) {
       return deliveryRangeJpaRepository.findByCityAndStreet(city, street);
    }
}
