package org.example.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.repository.OpeningHoursRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    public Map<DayOfWeek, OpeningHours> getOpeningHours(Restaurant restaurant) {
        return openingHoursRepository.findByRestaurant(restaurant);

    }

    public OpeningHours saveOpeningHours(OpeningHours openingHours) {
        return openingHoursRepository.save(openingHours);
    }

    public Map<String, Map<String, String>> formattedHours(List<RestaurantDTO> restaurants) {
        Map<String, Map<String, String>> restaurantGroupedHours = new LinkedHashMap<>();
        restaurants.forEach(restaurant -> {
            Map<String, List<DayOfWeek>> groupedHours = groupedHours(restaurant);
            Map<String, String> formattedHours = new LinkedHashMap<>();
            groupedHours.forEach((hours, days) -> {
                String firstDay = days.get(0).name().substring(0, 3);
                String lastDay = days.get(days.size() - 1).name().substring(0, 3);
                String dayRange = days.size() > 1 ? firstDay + " - " + lastDay : firstDay;
                formattedHours.put(dayRange, hours);
            });
            restaurantGroupedHours.put(restaurant.getRestaurantName(), formattedHours);
        });
        return restaurantGroupedHours;
    }

    private Map<String, List<DayOfWeek>> groupedHours(RestaurantDTO restaurant) {
        Map<String, List<DayOfWeek>> groupedHours = new LinkedHashMap<>();
        for (Map.Entry<DayOfWeek, OpeningHoursDTO> entry : restaurant.getOpeningHours().entrySet()) {
            String hours = entry.getValue().getOpenTime() + " - " + entry.getValue().getCloseTime();
            if (!groupedHours.containsKey(hours)) {
                groupedHours.put(hours, new ArrayList<>());
            }
            groupedHours.get(hours).add(entry.getKey());
        }
        return groupedHours;
    }
}


