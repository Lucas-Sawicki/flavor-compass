package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrdersDTO;
import org.example.business.CartService;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.example.business.dao.OrdersDAO;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Cart;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.domain.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersController {

    public static final String ORDERS = "/orders";
    private static final String ORDERS_BY_ID = "/orders/id";
    private final OrdersService ordersService;
    private final CustomerService customerService;
    private final RestaurantDAO restaurantDAO;
    private final CartService cartService;
    private final OrdersDAO ordersDAO;


    @GetMapping("/checkout")
    public String checkout(Model model) {
        Cart cart = cartService.getCart();
        if (cart == null) {
            return "redirect:/cart";
        }
        model.addAttribute("order", new OrdersDTO());
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("order") OrdersDTO orderDTO, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "checkout";
        }
        Customer customer = (Customer) session.getAttribute("customer");
        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (customer == null) {
            return "redirect:/login";
        }

        cartService.placeOrder(cartService.getCart(), customer, restaurant);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        // Dodaj dane potwierdzenia do modelu
        // ...
        return "confirmation";
    }
}
