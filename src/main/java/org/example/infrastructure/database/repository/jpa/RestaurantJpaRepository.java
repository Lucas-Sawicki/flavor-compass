package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {


    List<Restaurant> findRestaurantsByOwner(Owner owner);
    Boolean existsByEmail(String email);

}
