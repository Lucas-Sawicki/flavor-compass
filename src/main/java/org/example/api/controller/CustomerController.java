package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.api.dto.AddressDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.springframework.security.core.Authentication;
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
    public String homePage(Model model, Authentication authentication) {

        String currentCustomer = authentication.getName();

        var availableOrders = ordersService.availableOrders().stream()
                .map(ordersMapper::map)
                .toList();
        model.addAttribute("availableOrdersDTOs", availableOrders);
        return "customer_portal";
    }
    @GetMapping(value = CREATE_CUSTOMER)
    public String createCustomerPage(Model model) {
        AddressDTO addressDTO = new AddressDTO();
        model.addAttribute("addressDTO", addressDTO);
        return "create_new_customer";
    }

    @PostMapping(value = CREATE_CUSTOMER)
    public String createCustomer(
            @Valid @ModelAttribute(value = "customerRequestDTO") AddressDTO customer
    ) {


        return "customer_portal";
    }

}
