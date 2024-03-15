package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.domain.Owner;

import java.util.List;

public interface OrdersDAO {

    Orders getOrderByOrderNumber(Long orderNumber);

    Orders saveOrder(Orders orders);

    void delete(Long orderNumber);

    List<Orders> findByCustomer(Customer customer);

    List<Orders> findByOwner(Owner owner);


    void completeOrder(Long orderNumber);
}
