package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    @Override
    public List<Restaurant> findAvailableRestaurantsByOwner(Owner owner) {
        return restaurantJpaRepository.findRestaurantsByOwner(owner);
    }
}
