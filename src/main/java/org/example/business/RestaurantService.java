package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.dao.AddressDAO;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Address;
import org.example.domain.OpeningHours;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final AddressDAO addressDAO;
    private final RestaurantRepository restaurantRepository;
    private final OwnerService ownerService;
    private final OpeningHoursService openingHoursService;


//    public List<Restaurant> availableRestaurantsByOwner(String owner) {
//        Owner getOwner = ownerService.findOwnerByEmail(owner);
//        return restaurantDAO.findAvailableRestaurantsByOwner(getOwner);
//    }
    @Transactional
    public void addRestaurant(Restaurant restaurant) {
        Map<DayOfWeek, OpeningHours> savedOpeningHours = new TreeMap<>();
        for (Map.Entry<DayOfWeek, OpeningHours> entry : restaurant.getOpeningHours().entrySet()) {
            OpeningHours openingHours = entry.getValue();
            OpeningHours savedOpeningHoursEntity = openingHoursService.saveOpeningHours(openingHours);
            savedOpeningHours.put(entry.getKey(), savedOpeningHoursEntity);
        }
        Address address = restaurant.getAddress();
        Restaurant finalRestaurant = restaurant
                .withOpeningHours(savedOpeningHours)
                .withAddress(address);
        restaurantDAO.saveRestaurant(finalRestaurant);
    }
    public Boolean existsByEmail(String email) {
        return restaurantRepository.existsByEmail(email);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
    @Transactional
    public List<Restaurant> findRestaurantsByOwnerId(Integer ownerId) {
        Owner ownerById = ownerService.findOwnerById(ownerId);
        List<Restaurant> restaurants = restaurantRepository.findAvailableRestaurantsByOwner(ownerById);
        log.info("Available restaurant's: [{}]", restaurants.size());
        return restaurants;
    }

    public Restaurant findRestaurantsByEmail(String email) {
        return restaurantRepository.findRestaurantsByEmail(email);
    }
}
