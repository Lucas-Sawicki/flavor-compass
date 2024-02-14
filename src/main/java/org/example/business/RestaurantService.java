package org.example.business;

import lombok.AllArgsConstructor;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final OwnerService ownerService;

    public List<Restaurant> availableRestaurantsByOwner(String owner) {
        Owner getOwner = ownerService.findOwner(owner);
        return restaurantDAO.findAvailableRestaurantsByOwner(getOwner);
    }
}
