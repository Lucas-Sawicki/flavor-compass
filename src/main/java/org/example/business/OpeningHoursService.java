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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}
