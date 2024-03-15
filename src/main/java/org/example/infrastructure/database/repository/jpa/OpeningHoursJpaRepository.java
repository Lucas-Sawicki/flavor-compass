package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningHoursJpaRepository extends JpaRepository<OpeningHoursEntity, Integer> {

   List<OpeningHoursEntity> findByRestaurants(RestaurantEntity restaurant);
}
