package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.*;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    public static final String ADD_MENU = "/owner/add/menu";
    private static final String ADD_RESTAURANT = "/owner/add/restaurant";
    private final RestaurantService restaurantService;
    private final OwnerService ownerService;
    private final TokenService tokenService;
    private MenuItemService menuItemService;
    private final RestaurantMapper restaurantMapper;


    @GetMapping(value = ADD_RESTAURANT)
    public String homePage(Model model) {

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        Map<DayOfWeek, OpeningHoursDTO> openingHours = new TreeMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            openingHours.put(day, new OpeningHoursDTO());
        }
        restaurantDTO.setOpeningHours(openingHours);
        model.addAttribute("restaurantDTO", restaurantDTO);
        model.addAttribute("openingHoursDTO", new OpeningHoursDTO());
        model.addAttribute("openingHours", openingHours);
        return "add_restaurant";

    }

    @GetMapping(value = ADD_MENU)
    public String addMenu(Model model) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        Owner owner = ownerService.findOwnerByUser("ssawikk@gmail.com");
        List<Restaurant> restaurants = restaurantService.findRestaurantsByOwnerId(owner.getOwnerId());
        model.addAttribute("restaurantsList", restaurants);
        model.addAttribute("menuItemDTO", menuItemDTO);
        return "menu_portal";
    }
//    @GetMapping("/restaurant/{id}")
//    public String showRestaurant(@PathVariable Long id, Model model) {
//        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(id);
//        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
//        model.addAttribute("restaurant", restaurant);
//        return "restaurant";
//    }
        @PostMapping(value = ADD_MENU)
        public String addMenu(@ModelAttribute MenuItemDTO menuItemDTO) {
            Restaurant restaurantsByEmail = restaurantService.findRestaurantsByEmail("savona@gmail.com");
            menuItemService.addMenuItem(menuItemDTO, restaurantsByEmail);
        return "redirect:/success_register";
}
    @PostMapping(value = ADD_RESTAURANT)
    public ModelAndView addRestaurant(
            @ModelAttribute RestaurantDTO restaurantDTO,
            @ModelAttribute OpeningHoursDTO openingHoursDTO,
            HttpServletRequest req,
            BindingResult bindingResult
    ) {
        if (restaurantService.existsByEmail(restaurantDTO.getRestaurantEmail())) {
            return new ModelAndView("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        Owner owner = ownerService.findOwnerByUser("ssawikk@gmail.com");
        Restaurant restaurant = restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO(), owner);
        restaurantService.addRestaurant(restaurant);

        return new ModelAndView("redirect:/success_register");
    }
}



