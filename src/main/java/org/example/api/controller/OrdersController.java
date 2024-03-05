package org.example.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrdersDTO;
import org.example.business.*;
import org.example.business.dao.OrdersDAO;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.*;
import org.example.domain.exception.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(OrdersController.BASE_PATH)
@RequiredArgsConstructor
public class OrdersController {
    public static final String BASE_PATH = "/order";
    public static final String CHECKOUT = "/checkout";
    public static final String PLACE_ORDER = "/placeOrder";
    public static final String CONFIRMATION = "/confirmation";
    private static final String ORDERS_BY_ID = "/orders/{id}";
    private static final String CANCEL_ORDER = "/cancel";
    private static final String HISTORY = "/history";
    private static final String COMPLETED = "/complete";
    private static final String ORDER_ERROR = "/order_error";
    private final OrdersService ordersService;
    private final CustomerService customerService;
    private final UserService userService;
    private final OwnerService ownerService;

    private final CartService cartService;

    @GetMapping(value = CHECKOUT)
    public String checkout(Model model) {
        Cart cart = cartService.getCart();
        if (cart == null) {
            return "redirect:/cart";
        }
        model.addAttribute("order", new OrdersDTO());
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @PostMapping(value = PLACE_ORDER)
    public String placeOrder(@ModelAttribute("order") OrdersDTO orderDTO, BindingResult bindingResult, HttpSession session, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "checkout";
        }
        String email = principal.getName();
        Customer customerByEmail = customerService.findCustomerByEmail(email);

        List<MenuItem> menuItems = cartService.getCart().getItems().stream().map(OrderItem::getMenuItem).toList();
        List<Restaurant> restaurants = menuItems.stream().map(MenuItem::getRestaurant).toList();
        if (customerByEmail == null) {
            return "redirect:/auth/login";
        }

        List<Orders> orders = cartService.placeOrder(cartService.getCart(), customerByEmail, restaurants);
        String orderNumbers = orders.stream().map(order -> order.getOrderNumber().toString()).collect(Collectors.joining(","));
        return "redirect:/order/confirmation?orderNumbers=" + orderNumbers;

    }

    @GetMapping(value = CONFIRMATION)
    public String confirmation(@RequestParam String orderNumbers, Model model) {
        List<Long> orderNumberList = Arrays.stream(orderNumbers.split(",")).map(Long::valueOf).toList();
        List<Orders> orders = orderNumberList.stream().map(ordersService::getOrderByOrderNumber).collect(Collectors.toList());
        model.addAttribute("orders", orders);
        return "confirmation";
    }

    @DeleteMapping(value = CANCEL_ORDER)
    public String cancelOrder(@RequestParam Long orderNumber, HttpServletRequest request) {
        Orders order = ordersService.getOrderByOrderNumber(orderNumber);
        OffsetDateTime orderTime = order.getOrderDate();
        OffsetDateTime now = OffsetDateTime.now();

        long minutesSinceOrder = Duration.between(orderTime, now).toMinutes();

        if (minutesSinceOrder < 20) {
            ordersService.delete(order.getOrderNumber());
            return "redirect:/order/history";
        } else {
            return "redirect:/order/order_error";
        }
    }

    @GetMapping(value = HISTORY)
    public String orderHistory(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getOwner() != null) {
            Owner owner = user.getOwner();
            List<Orders> orders = ordersService.ordersHistoryForOwner(owner);
            List<Orders> pendingOrders = orders.stream()
                    .filter(order -> order.getStatus().equals("PENDING"))
                    .collect(Collectors.toList());

            List<Orders> completedOrders = orders.stream()
                    .filter(order -> order.getStatus().equals("COMPLETED"))
                    .collect(Collectors.toList());

            model.addAttribute("pendingOrders", pendingOrders);
            model.addAttribute("completedOrders", completedOrders);
            return "order_history_by_own";
        } else if (user.getCustomer() != null) {
            Customer customer = user.getCustomer();
            List<Orders> orders = ordersService.ordersHistoryForCustomer(customer);
            model.addAttribute("orders", orders);
            return "order_history_by_cst";
        } else {
            throw new RuntimeException("Sorry something goes wrong!");
        }
    }

    @GetMapping(value = ORDER_ERROR)
    public String orderError(Model model) {
        return "order_error";
    }
    @PatchMapping(value = COMPLETED)
    public String completeOrder(@RequestParam Long orderNumber) {
        ordersService.completeOrder(orderNumber);
        return "redirect:/order/history?continue";
    }
    private String dateFormatter(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
