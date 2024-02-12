package org.example.business.dao;

import org.example.domain.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDAO {


    List<Orders> findPendingOrders();
}
