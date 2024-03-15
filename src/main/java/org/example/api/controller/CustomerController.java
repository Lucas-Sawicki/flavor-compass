package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.CustomerService;
import org.example.business.DeliveryRangeService;
import org.example.business.OpeningHoursService;
import org.example.business.RestaurantService;
import org.example.domain.Customer;
import org.example.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(CustomerController.BASE_PATH)
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_ID = "/{customerID}";

    public static final String BASE_PATH = "/customer";

    private final CustomerService customerService;


    @GetMapping(value = CUSTOMER_ID)
    public String showCustomerPageByID(@PathVariable Integer customerID, Model model) {
        try {
            Customer findCustomer = customerService.findCustomerById(customerID);
            if (findCustomer != null) {
                model.addAttribute("customer", findCustomer);
                return "customer_portal";
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error displaying customer", e);
        }
    }

    @GetMapping()
    public String showCustomerPage(Model model) {
        return "customer_portal";
    }
}
