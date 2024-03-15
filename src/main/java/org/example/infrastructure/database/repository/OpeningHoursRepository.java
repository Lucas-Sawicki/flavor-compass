package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OpeningHoursDAO;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.OpeningHoursJpaRepository;
import org.example.infrastructure.database.repository.mapper.OpeningHoursEntityMapper;
import org.example.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class OpeningHoursRepository implements OpeningHoursDAO {

    private final OpeningHoursJpaRepository openingHoursJpaRepository;
    private final OpeningHoursEntityMapper openingHoursMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Map<DayOfWeek, OpeningHours> findByRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurant);
        List<OpeningHoursEntity> openingHoursEntities = openingHoursJpaRepository.findByRestaurants(restaurantEntity);
        return openingHoursEntities.stream()
                .collect(Collectors.toMap(
                        OpeningHoursEntity::getDayOfWeek,
                        openingHoursMapper::mapFromEntity
                ));
    }

    @Override
    public OpeningHours save(OpeningHours openingHours) {
        OpeningHoursEntity openingHoursEntity = openingHoursMapper.mapToEntity(openingHours);
        OpeningHoursEntity saved = openingHoursJpaRepository.saveAndFlush(openingHoursEntity);
        return openingHoursMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteById(Integer oldId) {
        openingHoursJpaRepository.deleteById(oldId);
    }
}
