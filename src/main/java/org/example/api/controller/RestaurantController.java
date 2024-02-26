package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.OpeningHoursService;
import org.example.business.RestaurantService;
import org.example.domain.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private static final String ADD_RESTAURANT = "/owner/add/restaurant";

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OpeningHoursService openingHoursService;

    @GetMapping(value = ADD_RESTAURANT)
    public String homePage(@RequestParam("token") String token, Model model) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        Map<DayOfWeek, OpeningHoursDTO> openingHours = new TreeMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            openingHours.put(day, new OpeningHoursDTO());
        }
        restaurantDTO.setOpeningHours(openingHours);
        model.addAttribute("restaurantDTO", restaurantDTO);
        model.addAttribute("addressDTO", new AddressDTO());
        model.addAttribute("openingHoursDTO", new OpeningHoursDTO());
        model.addAttribute("openingHours", openingHours);
        return "add_restaurant";

    }
//    @GetMapping("/restaurant/{id}")
//    public String showRestaurant(@PathVariable Long id, Model model) {
//        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(id);
//        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
//        model.addAttribute("restaurant", restaurant);
//        return "restaurant";
//    }

    @PostMapping(value = ADD_RESTAURANT)
    public ResponseEntity<String> addRestaurant(
            @ModelAttribute RestaurantDTO restaurantDTO,
            @ModelAttribute OpeningHoursDTO openingHoursDTO,
            BindingResult bindingResult
    ) {
        if (restaurantService.existsByEmail(restaurantDTO.getRestaurantEmail())) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }


        Restaurant restaurant = restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO());

        restaurantService.addRestaurant(restaurant);

        return ResponseEntity.ok("success_register");
    }


}
