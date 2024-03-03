package org.example.business;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class CartService {
    private final HttpSession httpSession;
    private final MenuItemService menuItemService;
    private final OrdersService ordersService;
    private final RestaurantMapper restaurantMapper;

    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addToCart(OrderItemDTO orderItemDTO) {
        Cart cart = getCart();

        MenuItem menuItem = menuItemService.findById(orderItemDTO.getMenuItemId());

        OrderItem orderItem = OrderItem.builder()
                .menuItem(menuItem)
                .quantity(orderItemDTO.getQuantity())
                .build();

        cart.addItem(orderItem);
    }


    public void removeFromCart(OrderItemDTO orderItemDTO) {
        Cart cart = getCart();
        cart.getItems().removeIf(item -> item.getMenuItem().getMenuItemId().equals(orderItemDTO.getMenuItemId()));
    }

    public Orders placeOrder(Cart cart, Customer customer, Restaurant restaurant) {
        OffsetDateTime date = OffsetDateTime.now();
        Orders order = Orders.builder()
                .restaurant(restaurant)
                .orderNumber(ordersService.generateOrderRequestNumber(date))
                .orderDate(date)
                .status("NEW")
                .customer(customer)
                .build();
        for (OrderItem item : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .menuItem(item.getMenuItem())
                    .quantity(item.getQuantity())
                    .order(order)
                    .build();
            order.getOrderItems().add(orderItem);
        }
        ordersService.saveOrder(order);
        httpSession.removeAttribute("cart");
        return order;
    }
}
