package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.example.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(CustomerController.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";
    
    private final CustomerService customerService;

@GetMapping(value = CUSTOMER)
public String listOfRestaurants(Model model){
    List<RestaurantDTO> restaurants = customerService.findAllRestaurants();
    model.addAttribute("restaurants", restaurants);
    return "customer_portal";
}

    @GetMapping("/customer/{customerID}")
    public String showOwner(@PathVariable Long customerID, Model model) {
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
