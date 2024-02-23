package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.Orders;

import java.util.List;

public interface OrdersDAO {
    public void createOrder(Orders order);

    List<Orders> findOrdersByCustomerEmail(String email);
    List<Orders> findOrdersByRestaurantEmail(String email);

    Orders findById(Integer id);
    void saveOrder(Customer customer);

//    public void updateStatusById(String id, OrderStatus status);
}
