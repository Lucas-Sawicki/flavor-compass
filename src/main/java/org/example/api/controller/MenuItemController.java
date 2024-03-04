package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuItemController {

    public static final String ADD_MENU = "/owner/add/menu";
    private final RestaurantService restaurantService;
    private final OwnerService ownerService;
    private final MenuItemService menuItemService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping(value = ADD_MENU)
    public String addMenuPage(Model model, HttpSession session, Principal principal) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        if (session.getAttribute("imageUrl") != null) {
            try {
                String imageUrl = (String) session.getAttribute("imageUrl");
                model.addAttribute("imageUrl", imageUrl);
                menuItemDTO.setPhotoUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String email = principal.getName();
        Owner owner = ownerService.findOwnerByUser(email);
        List<Restaurant> restaurants = restaurantService.findRestaurantsByOwnerId(owner.getOwnerId());
        model.addAttribute("restaurantsList", restaurants);
        model.addAttribute("menuItemDTO", menuItemDTO);
        return "add_menu";
    }
    @PostMapping(value = ADD_MENU)
    public String addMenu(@ModelAttribute MenuItemDTO menuItemDTO, HttpSession session) {
        String emailFromString = restaurantService.findEmailFromString(menuItemDTO.getRestaurantsList());
        Restaurant restaurantsByEmail = restaurantService.findRestaurantsByEmail(emailFromString);
        menuItemService.addMenuItem(menuItemDTO, restaurantsByEmail);
        session.setAttribute("restaurant", restaurantsByEmail);
        return "redirect:/auth/success_register";
    }
}
