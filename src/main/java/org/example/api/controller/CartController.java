package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderItemDTO;
import org.example.business.CartService;
import org.example.business.MenuItemService;
import org.example.domain.Cart;
import org.example.domain.MenuItem;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

    private static final String CART = "/cart";
    private static final String REMOVE = "/cart/remove";
    private final CartService cartService;
    private final MenuItemService menuItemService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("id") Integer menuItemId,
                           @Valid @RequestParam("quantity") @Max(value = 10, message = "Max 10") Integer quantity) {
        try {
            MenuItem menuItem = menuItemService.findById(menuItemId);
            if (menuItem != null) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setMenuItemId(menuItemId);
                orderItemDTO.setQuantity(quantity);
                cartService.addToCart(orderItemDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding to cart", e);
        }
        return "redirect:/cart";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(value = REMOVE)
    public String removeFromCart(@ModelAttribute OrderItemDTO orderItemDTO,
                                 HttpSession session) {
        try {
            cartService.removeFromCart(orderItemDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while removing from cart", e);
        }
        return "redirect:/cart";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = CART)
    public String showCart(Model model) {
        try {
            Cart cart = cartService.getCart();
            model.addAttribute("cart", cart);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while displaying cart", e);
        }
        return "cart";
    }
}
