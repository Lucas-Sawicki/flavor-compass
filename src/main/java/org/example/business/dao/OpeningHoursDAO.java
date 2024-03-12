package org.example.business.dao;

import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Repository
public interface OpeningHoursDAO {

   Map<DayOfWeek, OpeningHours> findByRestaurant(Restaurant restaurant);

   OpeningHours save(OpeningHours openingHours);

    void deleteById(Integer oldId);
}
