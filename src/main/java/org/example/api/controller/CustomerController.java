package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.business.OrdersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private static final String CUSTOMER = "/customer";

    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;


    @GetMapping(value = CUSTOMER)
    public String homePage(Model model) {
        var availableOrders = ordersService.availableOrders().stream()
                .map(ordersMapper::map)
                .toList();


        model.addAttribute("availableOrdersDTOs", availableOrders);

        return "customer_portal";
    }

}
