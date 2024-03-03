package org.example.business;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.dao.OrdersDAO;
import org.example.domain.*;
import org.example.infrastructure.database.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersDAO ordersDAO;
    private final HttpSession httpSession;


    public Long generateOrderRequestNumber(OffsetDateTime when) {
        UUID uniqueId = UUID.randomUUID();
        long timestamp = when.toInstant().toEpochMilli();
        long orderNumber = timestamp ^ uniqueId.getMostSignificantBits();
        return Math.abs(orderNumber);
    }

    public Orders getOrderFromCart() {
        return (Orders) httpSession.getAttribute("cart");
    }
    @Transactional
    public List<Orders> availableOrdersForCustomer(Customer customer) {
        List<Orders> ordersForCustomer = ordersRepository.findOrdersByCustomerEmail(customer.getUser().getEmail());
        log.info("Available orders: [{}]", ordersForCustomer.size());
        return  ordersForCustomer;
    }
    @Transactional
    public List<Orders> availableOrdersForRestaurant(Restaurant restaurant) {
        List<Orders> ordersForRestaurant = ordersRepository.findOrdersByRestaurantEmail(restaurant.getEmail());
        log.info("Available orders: [{}]", ordersForRestaurant.size());
        return  ordersForRestaurant;
    }
    @Transactional
    public void saveOrder(Orders orders) {
        ordersDAO.saveOrder(orders);
    }

}
