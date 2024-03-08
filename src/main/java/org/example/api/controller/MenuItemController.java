package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Validated
public class MenuItemController {

    public static final String ADD_MENU = "/owner/add/menu";
    private final RestaurantService restaurantService;
    private final OwnerService ownerService;
    private final MenuItemService menuItemService;

    @GetMapping(value = ADD_MENU)
    public String addMenuPage(Model model, HttpSession session, Principal principal) {
        try {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            if (session.getAttribute("imageUrl") != null) {
                String imageUrl = (String) session.getAttribute("imageUrl");
                model.addAttribute("imageUrl", imageUrl);
                menuItemDTO.setPhotoUrl(imageUrl);
            }
            String email = principal.getName();
            Owner owner = ownerService.findOwnerByUser(email);
            List<Restaurant> restaurants = restaurantService.findRestaurantsByOwnerId(owner.getOwnerId());
            model.addAttribute("restaurantsList", restaurants);
            model.addAttribute("menuItemDTO", menuItemDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while displaying add menu page", e);
        }
        return "add_menu";
    }

    @PostMapping(value = ADD_MENU)
    public String addMenu(@Valid @ModelAttribute MenuItemDTO menuItemDTO, HttpSession session, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", "));
            }
            String emailFromString = restaurantService.findEmailFromString(menuItemDTO.getRestaurantsList());
            Restaurant restaurantsByEmail = restaurantService.findRestaurantsByEmail(emailFromString);
            menuItemService.addMenuItem(menuItemDTO, restaurantsByEmail);
            session.setAttribute("restaurant", restaurantsByEmail);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding menu", e);
        }
        return "redirect:/auth/success_register";
    }
}
