package org.example.business.dao;

import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantDAO {
    List<Restaurant> findAvailableRestaurantsByOwner(Owner owner);
    Restaurant saveRestaurant(Restaurant restaurant);
    Boolean existsByEmail(String email);
}
