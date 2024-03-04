package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.*;
import org.example.domain.MenuItem;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class RestaurantController {
    private static final String ADD_RESTAURANT = "/owner/add/restaurant";
    private static final String RESTAURANT_BY_ID = "/restaurant/{id}";
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OwnerService ownerService;
    private final TokenService tokenService;
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

    @PostMapping(value = ADD_RESTAURANT)
    public ModelAndView addRestaurant(
            @ModelAttribute RestaurantDTO restaurantDTO,
            @ModelAttribute OpeningHoursDTO openingHoursDTO,
            Principal principal,
            HttpServletRequest req,
            BindingResult bindingResult
    ) {
        if (restaurantService.existsByEmail(restaurantDTO.getRestaurantEmail())) {
            return new ModelAndView("Email is already taken", HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Owner owner = ownerService.findOwnerByUser(email);
        Restaurant restaurant = restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO(), owner);
        restaurantService.addRestaurant(restaurant);

        return new ModelAndView("redirect:/auth/success_register");
    }

    @GetMapping(value = RESTAURANT_BY_ID)
    public String showMenuItems(@PathVariable Integer id,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "7") int size,
                                 @RequestParam(defaultValue = "name") String sortBy,
                                 Model model) {
        Page<MenuItem> menu = menuItemService.pagination(page, size, sortBy, id);
        RestaurantDTO restaurantDTO = restaurantService.findRestaurantById(id);
        List<MenuItemDTO> menuItems = menuItemService.findMenuByRestaurant(id);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        model.addAttribute("pagination", menu);
        model.addAttribute("orderItemDTO", orderItemDTO);
        model.addAttribute("restaurantDTO", restaurantDTO);
        model.addAttribute("menuItems", menuItems);
        return "restaurant_portal";
    }


}



