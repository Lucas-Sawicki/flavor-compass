package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.dao.OrdersDAO;
import org.example.domain.*;
import org.example.infrastructure.database.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersService {


    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OrderItemService orderItemService;
    private final OrdersRepository ordersRepository;
    private final OrdersDAO ordersDAO;




    @Transactional
    public void makeOrderRequest(Orders order) {
            saveOrderRequest(order);
    }

    private void saveOrderRequest(Orders request) {

//        Optional<MenuItem> menuItem = menuItemService.findMenuItem(request.getMenuItem());
//        Optional<OrderItem> orderItem = orderItemService.findOrderItem(request.withOrderItems());
////        Customer customer = customerService.findCustomerByEmail(request.getCustomer().getUser().getEmail());
//
//        Orders orderRequest = buildOrderRequest(request, customer);
//        Set<Orders> existingOrdersRequests = customer.getOrders();
//        existingOrdersRequests.add(orderRequest);
//        saveOrder(customer.withOrders(existingOrdersRequests));
    }



    private Orders buildOrderRequest(
            Orders orderRequest,
            Customer customer
    ) {
        OffsetDateTime when = OffsetDateTime.now();
        return Orders.builder()
                .orderNumber(generateOrderRequestNumber(when))
                .orderDate(when)
                .menuItem(orderRequest.getMenuItem())
                .orderItems(orderRequest.getOrderItems())
                .customer(customer)
                .restaurant(orderRequest.getRestaurant())
                .build();
    }

    private Long generateOrderRequestNumber(OffsetDateTime when) {
        UUID uniqueId = UUID.randomUUID();
        long timestamp = when.toInstant().toEpochMilli();
        long orderNumber = timestamp ^ uniqueId.getMostSignificantBits();
        return Math.abs(orderNumber);
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
    public void saveOrder(Customer customer) {
        ordersDAO.saveOrder(customer);
    }

}
