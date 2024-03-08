package org.example.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.business.dao.DeliverRangeDAO;
import org.example.domain.DeliveryRange;
import org.example.domain.Restaurant;
import org.example.domain.exception.CustomException;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.mapper.DeliveryRangeMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DeliveryRangeService {

    private final DeliverRangeDAO deliverRangeDAO;
    private final DeliveryRangeMapper deliveryRangeMapper;
    private final RestaurantService restaurantService;

    @Transactional
    public static String extractEmail(String input) {
        String emailRegex = "([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new CustomException("No email found in the input.");
        }
    }

    @Transactional
    public void addDeliveryPlaces(DeliveryRangeDTO deliveryRangeDTO) {
        try {
            String stringRestaurant = deliveryRangeDTO.getRestaurant();
            String email = extractEmail(stringRestaurant);
            Restaurant restaurant = restaurantService.findRestaurantsByEmail(email);
            for (String deliveryStreet : deliveryRangeDTO.getStreets()) {
                DeliveryRange deliveryRange = DeliveryRange.builder()
                        .city(deliveryRangeDTO.getCity())
                        .street(deliveryStreet)
                        .restaurants(Collections.singletonList(restaurant))
                        .build();

                deliverRangeDAO.saveDeliverRange(deliveryRange);
            }
        } catch (DataAccessException ex) {
            throw new CustomException("Error accessing data.", ex.getMessage());
        }

    }

    @Transactional
    private Map<String, List<Restaurant>> findRestaurantsByDeliveryStreet(String street) {
        try {
            List<DeliveryRange> findByStreet = deliverRangeDAO.findByStreet(street);
            Map<String, List<Restaurant>> restaurantMap = new HashMap<>();
            for (DeliveryRange deliveryRange : findByStreet) {
                restaurantMap.put(deliveryRange.getStreet(), deliveryRange.getRestaurants());
            }
            return restaurantMap;
        } catch (DataAccessException ex) {
            throw new CustomException("Error accessing data.", ex.getMessage());
        }
    }

    @Transactional
    private Map<String, List<Restaurant>> findRestaurantsByDeliveryCity(String city) {
        try {
            List<DeliveryRange> findByCity = deliverRangeDAO.findByCity(city);
            Map<String, List<Restaurant>> restaurantMap = new HashMap<>();
            for (DeliveryRange deliveryRange : findByCity) {
                restaurantMap.put(deliveryRange.getCity(), deliveryRange.getRestaurants());
            }
            return restaurantMap;
        } catch (DataAccessException ex) {
            throw new CustomException("Error accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public List<Restaurant> processingFindRestaurants(DeliveryRangeDTO deliveryRangeDTO) {
        try {
            String street = deliveryRangeDTO.getStreet();
            String city = deliveryRangeDTO.getCity();
            List<Restaurant> restaurants = new ArrayList<>();

            if (street != null && !street.isEmpty() && city != null && !city.isEmpty()) {
                street = prefix(street);
                DeliveryRangeEntity deliveryRangeEntity = deliverRangeDAO.findByCityAndStreet(city, street)
                        .orElseThrow(() -> new NotFoundException("Any restaurant can't be found for this city and street"));
                DeliveryRange deliveryRange = deliveryRangeMapper.mapFromEntity(deliveryRangeEntity);
                return deliveryRange.getRestaurants();
            } else if (city != null && !city.isEmpty()) {
                Optional<Map<String, List<Restaurant>>> restaurantsByDeliveryCity =
                        Optional.of(findRestaurantsByDeliveryCity(city));
                restaurants = restaurantsByDeliveryCity.get().getOrDefault(city, new ArrayList<>());
            } else if (street != null && !street.isEmpty()) {
                street = prefix(street);
                Optional<Map<String, List<Restaurant>>> restaurantsByDeliveryStreet =
                        Optional.of(findRestaurantsByDeliveryStreet(street));
                restaurants = restaurantsByDeliveryStreet.get().getOrDefault(street, new ArrayList<>());
            }
            return restaurants;
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Invalid data.", ex.getMessage());
        }
    }

    @NotNull
    private String prefix(String street) {
        if (!street.startsWith("ul.")) {
            street = "ul. " + street;
        }
        return street;
    }
}