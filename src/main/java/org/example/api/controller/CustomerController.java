package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.api.dto.CustomerRequestDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.example.domain.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private static final String CUSTOMER = "/customer";
    private static final String CREATE_CUSTOMER = "/create/customer";

    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
    private final UserMapper userMapper;
    private final CustomerService customerService;


    @GetMapping(value = CUSTOMER)
    public String homePage(Model model) {
        var availableOrders = ordersService.availableOrders().stream()
                .map(ordersMapper::map)
                .toList();
        model.addAttribute("availableOrdersDTOs", availableOrders);
        return "customer_portal";
    }
    @GetMapping(value = CREATE_CUSTOMER)
    public String createCustomerPage(Model model) {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        model.addAttribute("customerRequestDTO", customerRequestDTO);
        return "create_new_user";
    }

    @PostMapping(value = CREATE_CUSTOMER)
    public String createCustomer(
            @Valid @ModelAttribute(value = "customerRequestDTO") CustomerRequestDTO customer
    ) {
        Customer mappedCustomer = userMapper.map(customer);
        customerService.saveCustomer(mappedCustomer);
        return "customer_portal";
    }

}
