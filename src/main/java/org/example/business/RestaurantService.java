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
import org.example.domain.exception.CustomException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantMapper restaurantMapper;
    private final OwnerService ownerService;
    private final OpeningHoursService openingHoursService;

    @Transactional
    public void addRestaurant(Restaurant restaurant) {
        try {
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
        } catch (DataAccessException ex) {
            throw new CustomException("Error while saving restaurant.", ex.getMessage());
        }
    }

    @Transactional
    public Boolean existsByEmail(String email) {
        try {
            return restaurantDAO.existsByEmail(email);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while checking if email exists.", ex.getMessage());
        }
    }

    @Transactional
    public List<Restaurant> findRestaurantsByOwnerId(Integer ownerId) {
        try {
            Owner ownerById = ownerService.findOwnerById(ownerId);
            List<Restaurant> restaurants = restaurantDAO.findAvailableRestaurantsByOwner(ownerById);
            log.info("Available restaurant's: [{}]", restaurants.size());
            return restaurants;
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding restaurants by owner id.", ex.getMessage());
        }
    }

    @Transactional
    public Restaurant findRestaurantsByEmail(String email) {
        try {
            return restaurantDAO.findRestaurantsByEmail(email);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding restaurant by email.", ex.getMessage());
        }
    }

    @Transactional
    public String findEmailFromString(String restaurant) {
        try {
            Pattern emailPattern = Pattern.compile("email=([^,]*)");
            Matcher matcher = emailPattern.matcher(restaurant);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        } catch (PatternSyntaxException ex) {
            throw new CustomException("Error while in email pattern syntax.", ex.getMessage());
        }
    }

    @Transactional
    public RestaurantDTO findRestaurantById(Integer restaurantId) {
        try {
            Restaurant restaurantById = restaurantDAO.findRestaurantById(restaurantId);
            return restaurantMapper.map(restaurantById);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding restaurant by id.", ex.getMessage());
        }
    }

    @Transactional
    public Page<Restaurant> pagination(int page, int size, String sortBy, List<Restaurant> restaurants) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), restaurants.size());

            List<Restaurant> subList = new ArrayList<>(restaurants.subList(start, end));
            subList.sort(Comparator.comparing(Restaurant::getLocalName));

            return new PageImpl<>(subList, pageRequest, restaurants.size());
        } catch (DataAccessException ex) {
            throw new CustomException("Error while paginating restaurants.", ex.getMessage());
        }
    }


}
