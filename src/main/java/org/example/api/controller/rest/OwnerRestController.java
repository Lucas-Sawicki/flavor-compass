package org.example.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.AddressMapper;
import org.example.api.dto.mapper.MenuItemMapper;
import org.example.api.dto.mapper.OpeningHoursMapper;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.api.dto.rest.RestRestaurantUpdateDTO;
import org.example.business.*;
import org.example.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(OwnerRestController.BASE_PATH)
@Validated
public class OwnerRestController {


    public static final String BASE_PATH = "/api";

    private static final String ADD_RESTAURANT = "/add/restaurant";
    private static final String ADD_MENU_ITEM = "/add/menu";
    private static final String UPDATE_OPENING_HOURS = "/update/opening_hours";
    private static final String UPDATE_ADDRESS = "/update/address";
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OpeningHoursService openingHoursService;
    private final OwnerService ownerService;
    private final MenuItemService menuItemService;
    private final AddressMapper addressMapper;


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = ADD_RESTAURANT)
    public ResponseEntity<RestaurantDTO> addRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO,
            Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            Owner owner = user.get().getOwner();
            Restaurant restaurant = restaurantMapper.mapForApi(restaurantDTO, restaurantDTO.getAddressDTO(), owner);
            restaurantService.addRestaurant(restaurant);
        }
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = ADD_MENU_ITEM)
    public ResponseEntity<MenuItemDTO> addMenu(
            @Valid @RequestBody RestRestaurantUpdateDTO restRestaurantUpdateDTO,
            Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            Restaurant restaurant = restaurantService.findRestaurantsByEmail(restRestaurantUpdateDTO.getRestaurantEmail());
            Owner owner = ownerService.findOwnerById(restaurant.getOwner().getOwnerId());
            String ownerEmail = owner.getUser().getEmail();
            if (!email.equals(ownerEmail)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            MenuItemDTO menuItemDTO = restRestaurantUpdateDTO.getMenuItemDTO();
            List<MenuItemDTO> menu = menuItemService.findMenuByRestaurant(restaurant.getRestaurantId());
            for (MenuItemDTO item : menu) {
                if (item.equals(menuItemDTO)) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            menuItemService.addMenuItem(menuItemDTO, restaurant);
            return new ResponseEntity<>(menuItemDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping(value = UPDATE_OPENING_HOURS)
    public ResponseEntity<RestaurantDTO> updateRestaurantOpeningHours(
            @Valid @RequestBody RestRestaurantUpdateDTO restRestaurantUpdateDTO,
            Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            Restaurant restaurant = restaurantService.findRestaurantsByEmail(restRestaurantUpdateDTO.getRestaurantEmail());
            Owner owner = ownerService.findOwnerById(restaurant.getOwner().getOwnerId());
            String ownerEmail = owner.getUser().getEmail();

            if (!email.equals(ownerEmail)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Map<DayOfWeek, OpeningHours> currentOpeningHours = restaurant.getOpeningHours();
            List<Integer> oldOpeningHoursId = new ArrayList<>();

            Map<DayOfWeek, OpeningHoursDTO> openingHoursDTOMap = restRestaurantUpdateDTO.getOpeningHoursDTO();
            if (openingHoursDTOMap != null) {
                for (Map.Entry<DayOfWeek, OpeningHoursDTO> entry : openingHoursDTOMap.entrySet()) {
                    DayOfWeek day = entry.getKey();
                    OpeningHoursDTO openingHoursDTO = entry.getValue();
                    if (currentOpeningHours.containsKey(day)) {
                        Integer oldId = currentOpeningHours.get(day).getOpeningHoursId();
                        boolean isUpdated = openingHoursService.updateDay(currentOpeningHours, openingHoursDTO);
                        if (isUpdated) {
                            oldOpeningHoursId.add(oldId);
                        }
                    }
                }
            }
            restaurantService.saveRestaurant(restaurant.withOpeningHours(currentOpeningHours));
            oldOpeningHoursId.forEach(openingHoursService::deleteById);
            RestaurantDTO restaurantDTO = restaurantMapper.map(restaurant);
            return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping(value = UPDATE_ADDRESS)
    public ResponseEntity<String> updateRestaurantAddress(
            @Valid @RequestBody RestRestaurantUpdateDTO restRestaurantUpdateDTO,
            Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.findByEmail(email);
        Restaurant restaurant = restaurantService.findRestaurantsByEmail(restRestaurantUpdateDTO.getRestaurantEmail());
        if (user.isPresent()) {
            Owner owner = ownerService.findOwnerById(restaurant.getOwner().getOwnerId());
            String ownerEmail = owner.getUser().getEmail();

            if (!email.equals(ownerEmail)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sorry this isn't your restaurant");
            }

            Address address = addressMapper.map(restRestaurantUpdateDTO.getAddressDTO());

            restaurantService.updateAddress(address, restaurant);
        }
        return ResponseEntity.ok(String.format("Address for restaurant: [%s] updated successfully", restaurant));
    }
}
