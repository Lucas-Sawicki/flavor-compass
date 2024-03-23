package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.OpeningHoursService;
import org.example.business.RestaurantService;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    public static final String FIND = "/find";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OpeningHoursService openingHoursService;
    private final DeliveryRangeService deliveryRangeService;

    @GetMapping(value = FIND)
    public String findRestaurants(Model model) {
        return "find";
    }

    @PostMapping(value = FIND)
    public String listOfRestaurants(Model model,
                                    @ModelAttribute DeliveryRangeDTO deliveryRangeDTO,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "7") int size,
                                    @RequestParam(defaultValue = "localName") String sortBy) {

            List<Restaurant> restaurantList = deliveryRangeService.processingFindRestaurants(deliveryRangeDTO);
            Page<Restaurant> restaurants = restaurantService.pagination(page, size, sortBy, restaurantList);
            List<RestaurantDTO> restaurantDTOS = restaurants.getContent().stream().map(restaurantMapper::map).toList();
            Map<String, Map<String, String>> restaurantGroupedHours = openingHoursService.formattedHours(restaurantDTOS);
            model.addAttribute("restaurants", restaurantDTOS);
            model.addAttribute("pagination", restaurants);
            model.addAttribute("restaurantGroupedHours", restaurantGroupedHours);
        return "find_restaurants";
    }
}
