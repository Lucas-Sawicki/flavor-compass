package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.example.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    @Override
    public List<Restaurant> findAvailableRestaurantsByOwner(Owner owner) {
        return restaurantJpaRepository.findRestaurantsByOwner(owner);
    }
    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity toSave = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity saved = restaurantJpaRepository.saveAndFlush(toSave);
        return restaurantEntityMapper.mapFromEntity(saved);
    }
    @Override
    public Boolean existsByEmail(String email) {
        return restaurantJpaRepository.existsByEmail(email);
    }
}
