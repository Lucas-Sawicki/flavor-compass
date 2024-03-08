package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.api.dto.OrderItemDTO;
import org.example.domain.*;
import org.example.domain.exception.CustomException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final HttpSession httpSession;
    private final MenuItemService menuItemService;
    private final OrdersService ordersService;
    private final OrderItemService orderItemService;

    @Transactional
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        return cart;
    }

    @Transactional
    public void addToCart(OrderItemDTO orderItemDTO) {
        try {
            Cart cart = getCart();

            MenuItem menuItem = menuItemService.findById(orderItemDTO.getMenuItemId());

            OrderItem orderItem = OrderItem.builder()
                    .menuItem(menuItem)
                    .quantity(orderItemDTO.getQuantity())
                    .build();

            cart.addItem(orderItem);
        } catch (EntityNotFoundException ex) {
            throw new CustomException("Menu item not found.", ex.getMessage());
        }

    }

    @Transactional
    public void removeFromCart(OrderItemDTO orderItemDTO) {
        Cart cart = getCart();
        cart.getItems().removeIf(item -> item.getMenuItem().getMenuItemId().equals(orderItemDTO.getMenuItemId()));
    }

    @Transactional
    public List<Orders> placeOrder(Cart cart, Customer customer, List<Restaurant> restaurants) {
        try {
            List<Orders> ordersList = new ArrayList<>();
            OffsetDateTime date = OffsetDateTime.now();
            for (Restaurant restaurant : restaurants) {
                List<OrderItem> orderItemsList = new ArrayList<>();
                Orders order = Orders.builder()
                        .restaurant(restaurant)
                        .orderNumber(ordersService.generateOrderRequestNumber(date))
                        .orderDate(date)
                        .status("PENDING")
                        .customer(customer)
                        .orderItems(orderItemsList)
                        .build();

                Orders savedOrder = ordersService.saveOrder(order);

                for (OrderItem item : cart.getItems()) {
                    if (item.getMenuItem().getRestaurant().equals(restaurant)) {
                        OrderItem orderItem = OrderItem.builder()
                                .menuItem(item.getMenuItem())
                                .quantity(item.getQuantity())
                                .order(savedOrder)
                                .build();
                        orderItemsList.add(orderItem);
                    }
                }
                Orders withOrderItems = savedOrder.withOrderItems(orderItemsList);
                withOrderItems.getOrderItems().forEach(orderItemService::save);
                ordersList.add(withOrderItems);
            }
            httpSession.removeAttribute("cart");
            return ordersList;
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Invalid order data.", ex.getMessage());
        }
    }
}
