package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.UserDTO;
import org.example.business.RegistrationProcessingService;
import org.example.business.RestaurantService;
import org.example.domain.exception.UserAlreadyExistException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private static final String OWNER = "/owner";
    private static final String CREATE_OWNER = "/create/owner";
    @ModelAttribute("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }


    private final RestaurantService restaurantService;


    private final RegistrationProcessingService registrationProcessingService;
    @GetMapping(value = OWNER)
    public String homePage(Model model, Authentication authentication) {

        String currentOwner = authentication.getName();

        var availableRestaurants = restaurantService.availableRestaurantsByOwner(currentOwner).stream()
                .toList();
        model.addAttribute("availableRestaurantsDTOs", availableRestaurants);
        return "owner_portal";
    }

    @GetMapping(value = CREATE_OWNER)
    public String createOwnerPage(Model model) {
        return "create_new_owner";
    }


}
