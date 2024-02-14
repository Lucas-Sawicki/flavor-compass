package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.CustomerDTO;
import org.example.api.dto.OwnerRequestDTO;
import org.example.api.dto.mapper.OrdersMapper;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.OrdersService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private static final String OWNER = "/owner";
    private static final String CREATE_OWNER = "/owner/create";

    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
    private final RestaurantService restaurantService;
    private final UserMapper userMapper;
    private final OwnerService ownerService;
    private final RestaurantMapper restaurantMapper;

    CustomerDTO ownerDTO;


    @GetMapping(value = OWNER)
    public String homePage(Model model) {

        String owner = "owner";

        var availableRestaurants = restaurantService.availableRestaurantsByOwner(owner).stream()
                .toList();
        model.addAttribute("availableRestaurantsDTOs", availableRestaurants);
        return "owner_portal";
    }

    @GetMapping(value = CREATE_OWNER)
    public String createOwnerPage(Model model) {
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        model.addAttribute("ownerRequestDTO", ownerRequestDTO);
        return "create_new_owner";
    }

    @PostMapping(value = CREATE_OWNER)
    public String createOwner(
            @Valid @ModelAttribute(value = "ownerRequestDTO") OwnerRequestDTO owner
    ) {

        Owner mappedUser = userMapper.map(owner);
        ownerService.saveOwner(mappedUser);

        return "owner_portal";
    }

}
