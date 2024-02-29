package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.example.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public List<Restaurant> findAvailableRestaurantsByOwner(Owner owner) {
        OwnerEntity ownerEntity = ownerEntityMapper.mapToEntity(owner);
        List<RestaurantEntity> restaurantEntities = restaurantJpaRepository.findRestaurantsByOwner(ownerEntity);
        return restaurantEntities.stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantEntity toSave = restaurantEntityMapper.mapToEntity(restaurant);
        restaurantJpaRepository.saveAndFlush(toSave);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return restaurantJpaRepository.existsByEmail(email);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantJpaRepository.findAll().stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Restaurant findRestaurantsByEmail(String email) {
        RestaurantEntity entity = restaurantJpaRepository.findByEmail(email);
        return restaurantEntityMapper.mapFromEntity(entity);
    }
}
