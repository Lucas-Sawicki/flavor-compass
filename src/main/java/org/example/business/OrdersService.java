package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.dao.OrdersDAO;
import org.example.domain.Customer;
import org.example.domain.MenuItem;
import org.example.domain.OrderItem;
import org.example.domain.Orders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersService {

    private final CustomerService customerService;
    private final MenuItemService menuItemService;
    private final OrderItemService orderItemService;
    private final OrdersDAO ordersDAO;



    @Transactional
    public void makeOrderRequest(Orders order) {
            saveOrderRequest(order);
    }

    private void saveOrderRequest(Orders request) {

//        Optional<MenuItem> menuItem = menuItemService.findMenuItem(request.getMenuItem());
//        Optional<OrderItem> orderItem = orderItemService.findOrderItem(request.withOrderItems());
        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());

        Orders orderRequest = buildOrderRequest(request, customer);
        Set<Orders> existingOrdersRequests = customer.getOrders();
        existingOrdersRequests.add(orderRequest);
        customerService.saveOrder(customer.withOrders(existingOrdersRequests));
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
    public List<Orders> availableOrders() {
        List<Orders> availableOrders = ordersDAO.findPendingOrders();
        log.info("Available orders: [{}]", availableOrders.size());
        return  availableOrders;
    }

}
