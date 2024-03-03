package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderItemDTO;
import org.example.business.CartService;
import org.example.business.MenuItemService;
import org.example.domain.Cart;
import org.example.domain.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {

    private static final String CART = "/cart";
    private static final String REMOVE = "/cart/remove";
    private final CartService cartService;
    private final MenuItemService menuItemService;

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("id") Integer menuItemId,
                            @RequestParam("quantity") Integer quantity,
                            HttpSession session) {
        MenuItem menuItem = menuItemService.findById(menuItemId);
        if (menuItem != null) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setMenuItemId(menuItemId);
            orderItemDTO.setQuantity(quantity);
            cartService.addToCart(orderItemDTO);
        }
        return "redirect:/cart";
    }

    @PostMapping(value = REMOVE)
    public String removeFromCart(@ModelAttribute OrderItemDTO orderItemDTO,
            HttpSession session) {
        cartService.removeFromCart(orderItemDTO);
        return "redirect:/cart";
    }
    @GetMapping(value = CART)
    public String showCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }
}
