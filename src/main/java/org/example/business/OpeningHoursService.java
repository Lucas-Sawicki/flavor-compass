package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.OpeningHoursMapper;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.repository.OpeningHoursRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;
    private final OpeningHoursMapper openingHoursMapper;

    @Transactional
    public Map<DayOfWeek, OpeningHours> getOpeningHours(Restaurant restaurant) {
        try {
            return openingHoursRepository.findByRestaurant(restaurant);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public OpeningHours saveOpeningHours(OpeningHours openingHours) {
        try {
            return openingHoursRepository.save(openingHours);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while saving opening hours.", ex.getMessage());
        }
    }

    @Transactional
    public Map<String, Map<String, String>> formattedHours(List<RestaurantDTO> restaurants) {
        try {
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
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Invalid restaurant data.", ex.getMessage());
        }
    }

    @Transactional
    private Map<String, List<DayOfWeek>> groupedHours(RestaurantDTO restaurant) {
        try {
            Map<String, List<DayOfWeek>> groupedHours = new LinkedHashMap<>();
            for (Map.Entry<DayOfWeek, OpeningHoursDTO> entry : restaurant.getOpeningHours().entrySet()) {
                String hours = entry.getValue().getOpenTime() + " - " + entry.getValue().getCloseTime();
                if (!groupedHours.containsKey(hours)) {
                    groupedHours.put(hours, new ArrayList<>());
                }
                groupedHours.get(hours).add(entry.getKey());
            }
            return groupedHours;
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Invalid restaurant data.", ex.getMessage());
        }
    }

    @Transactional
    public boolean updateDay(Map<DayOfWeek, OpeningHours> currentOpeningHours, OpeningHoursDTO openingHoursDTO) {
        OpeningHours mapped = openingHoursMapper.mapFromDto(openingHoursDTO);
        DayOfWeek day = openingHoursDTO.getDay();
        OpeningHours currentOpeningHour = currentOpeningHours.get(day);
        if (currentOpeningHour == null || !currentOpeningHour.equals(mapped)) {
            currentOpeningHours.put(day, mapped);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteById(Integer oldId) {
        openingHoursRepository.deleteById(oldId);
    }

}


