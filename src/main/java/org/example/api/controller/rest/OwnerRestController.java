package org.example.api.controller.rest;

import lombok.AllArgsConstructor;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.TokenService;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping(OwnerRestController.BASE_PATH)
public class OwnerRestController {


    public static final String BASE_PATH = "/api/owner";

    private static final String ADD_RESTAURANT = "/add/restaurant";
    private TokenService tokenService;
    private OwnerService ownerService;
    private RestaurantService restaurantService;
    private RestaurantMapper restaurantMapper;


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = ADD_RESTAURANT)
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO, Principal principal) {
        String email = principal.getName();
        Owner ownerByEmail = ownerService.findOwnerByUser(email);
        Restaurant restaurant = restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO(), ownerByEmail);
        restaurantService.addRestaurant(restaurant);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

}
