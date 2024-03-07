package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Address;
import org.example.domain.OpeningHours;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantMapper restaurantMapper;
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
        return restaurantDAO.existsByEmail(email);
    }

    public List<Restaurant> findAll() {
        return restaurantDAO.findAllByRestaurantId();
    }
    @Transactional
    public List<Restaurant> findRestaurantsByOwnerId(Integer ownerId) {
        Owner ownerById = ownerService.findOwnerById(ownerId);
        List<Restaurant> restaurants = restaurantDAO.findAvailableRestaurantsByOwner(ownerById);
        log.info("Available restaurant's: [{}]", restaurants.size());
        return restaurants;
    }

    public Restaurant findRestaurantsByEmail(String email) {
        return restaurantDAO.findRestaurantsByEmail(email);
    }

    public String findEmailFromString(String restaurant) {
        Pattern emailPattern = Pattern.compile("email=([^,]*)");
        Matcher matcher = emailPattern.matcher(restaurant);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public RestaurantDTO findRestaurantById(Integer restaurantId) {
        Restaurant restaurantById = restaurantDAO.findRestaurantById(restaurantId);
        return restaurantMapper.map(restaurantById);
    }


    public Page<Restaurant> pagination(int page, int size, String sortBy, List<Restaurant> restaurants) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), restaurants.size());

        List<Restaurant> subList = new ArrayList<>(restaurants.subList(start, end));
        subList.sort(Comparator.comparing(Restaurant::getLocalName));

        return new PageImpl<>(subList, pageRequest, restaurants.size());
    }



}
