package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.example.api.dto.AddressDTO;
import org.example.api.dto.OrdersDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.example.domain.Customer;
import org.example.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private static final String CUSTOMER = "/customer";
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
    private final UserMapper userMapper;
    private final CustomerService customerService;


    @RequestMapping(value = CUSTOMER, method = RequestMethod.GET)
    public String homePage(
            @Valid @ModelAttribute(value = "OrdersDTO") OrdersDTO dto,
            Model model, Authentication authentication) {

        if (authentication.isAuthenticated()) {
            String currentCustomer = authentication.getName();
            Customer customerFound = customerService.findCustomer(currentCustomer);


            var availableOrders = ordersService.availableOrdersForCustomer(customerFound).stream()
                    .map(ordersMapper::map)
                    .toList();
            model.addAttribute("OrdersDTO", availableOrders);
            return "customer_portal";
        } else {
            throw new NotFoundException("Cannot log in");
        }
    }
}
