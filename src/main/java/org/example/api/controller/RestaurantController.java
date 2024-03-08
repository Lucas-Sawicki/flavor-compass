package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.MenuItem;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Validated
public class RestaurantController {
    private static final String ADD_RESTAURANT = "/owner/add/restaurant";
    private static final String RESTAURANT_BY_ID = "/restaurant/{id}";
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OwnerService ownerService;
    private final RestaurantMapper restaurantMapper;


    @GetMapping(value = ADD_RESTAURANT)
    public String homePage(Model model) {
        try {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            Map<DayOfWeek, OpeningHoursDTO> openingHours = new TreeMap<>();

            for (DayOfWeek day : DayOfWeek.values()) {
                openingHours.put(day, new OpeningHoursDTO());
            }
            restaurantDTO.setOpeningHours(openingHours);
            model.addAttribute("restaurantDTO", restaurantDTO);
            model.addAttribute("openingHoursDTO", new OpeningHoursDTO());
            model.addAttribute("openingHours", openingHours);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while displaying add restaurant page", e);
        }
        return "add_restaurant";
    }

    @PostMapping(value = ADD_RESTAURANT)
    public ModelAndView addRestaurant(
            @Valid @ModelAttribute RestaurantDTO restaurantDTO,
            @Valid @ModelAttribute OpeningHoursDTO openingHoursDTO,
            Principal principal,
            Model model,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", "));
            }
            if (restaurantService.existsByEmail(restaurantDTO.getRestaurantEmail())) {
                return new ModelAndView("Email is already taken", HttpStatus.BAD_REQUEST);
            }
            String email = principal.getName();
            Owner owner = ownerService.findOwnerByUser(email);
            Restaurant restaurant = restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO(), owner);
            restaurantService.addRestaurant(restaurant);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding restaurant", e);
        }
        return new ModelAndView("redirect:/auth/success_register");
    }

    @GetMapping(value = RESTAURANT_BY_ID)
    public String showMenuItems(@PathVariable Integer id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "7") int size,
                                @RequestParam(defaultValue = "name") String sortBy,
                                Model model) {
        try {
            Page<MenuItem> menu = menuItemService.pagination(page, size, sortBy, id);
            RestaurantDTO restaurantDTO = restaurantService.findRestaurantById(id);
            List<MenuItemDTO> menuItems = menuItemService.findMenuByRestaurant(id);
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            model.addAttribute("pagination", menu);
            model.addAttribute("orderItemDTO", orderItemDTO);
            model.addAttribute("restaurantDTO", restaurantDTO);
            model.addAttribute("menuItems", menuItems);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while displaying menu items", e);
        }
        return "restaurant_portal";
    }

}



