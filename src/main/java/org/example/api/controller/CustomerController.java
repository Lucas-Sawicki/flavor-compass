package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.CustomerService;
import org.example.business.OpeningHoursService;
import org.example.business.RestaurantService;
import org.example.domain.Customer;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(CustomerController.BASE_PATH)
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_ID = "/{customerID}";
    public static final String FIND = "/find";
    public static final String BASE_PATH = "/customer";

    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OpeningHoursService openingHoursService;

    @GetMapping(value = FIND)
    public String listOfRestaurants(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "localName") String sortBy) {
        Page<Restaurant> restaurants = restaurantService.pagination(page, size, sortBy);
        List<RestaurantDTO> restaurantsDTO = restaurants.map(restaurantMapper::map).toList();
        Map<String, Map<String, String>> restaurantGroupedHours = openingHoursService.formattedHours(restaurantsDTO);
        model.addAttribute("restaurants", restaurantsDTO);
        model.addAttribute("pagination", restaurants);
        model.addAttribute("restaurantGroupedHours", restaurantGroupedHours);
        return "find_restaurants";
    }
    @GetMapping(value = CUSTOMER_ID)
    public String showOwner(@PathVariable Integer customerID, Model model) {
        Customer findCustomer = customerService.findCustomerById(customerID);
        log.info("Customer found");
        if (findCustomer != null) {
            model.addAttribute("customer", findCustomer);
            return "customer_portal";
        } else {
            return "error";
        }
    }
}
