package org.example.business.dao;

import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantDAO {
    List<Restaurant> findAvailableRestaurantsByOwner(Owner owner);
    Restaurant saveRestaurant(Restaurant restaurant);
    Boolean existsByEmail(String email);

    List<Restaurant> findAllByRestaurantId();

    Restaurant findRestaurantsByEmail(String email);

    Restaurant findRestaurantById(Integer restaurantId);

}
