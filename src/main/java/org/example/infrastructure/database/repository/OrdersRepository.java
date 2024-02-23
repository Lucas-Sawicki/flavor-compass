package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrdersDAO;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.repository.jpa.OrdersJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
import org.example.infrastructure.database.repository.mapper.OrdersEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrdersRepository implements OrdersDAO {

    private final OrdersJpaRepository ordersJpaRepository;
    private final OrdersEntityMapper ordersEntityMapper;
    private final CustomerEntityMapper customerEntityMapper;


    @Override
    public void createOrder(Orders order) {
        OrdersEntity ordersEntity = ordersEntityMapper.mapToEntity(order);
        ordersJpaRepository.save(ordersEntity);
    }

    @Override
    public List<Orders> findOrdersByCustomerEmail(String email) {
        return ordersJpaRepository.findOrdersByCustomerEmail(email).stream()
                .map(ordersEntityMapper::mapFromEntity)
                .toList();
    }
    @Override
    public List<Orders> findOrdersByRestaurantEmail(String email) {
        return ordersJpaRepository.findOrdersByRestaurantEmail(email).stream()
                .map(ordersEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Orders findById(Integer id) {
        Optional<OrdersEntity> ordersEntity = ordersJpaRepository.findById(id);
        if (ordersEntity.isPresent()) {
            OrdersEntity entity = ordersEntity.get();
            return ordersEntityMapper.mapFromEntity(entity);
        } else {
            throw new RuntimeException(String.format("Cant find order with id: [%s]", id));
        }
    }


    @Override
    public void saveOrder(Customer customer) {
        List<OrdersEntity> orders = customer.getOrders().stream()
                .map(ordersEntityMapper::mapToEntity)
                .toList();

        orders.forEach(request -> request.setCustomer(customerEntityMapper.mapToEntity(customer)));
        ordersJpaRepository.saveAllAndFlush(orders);
    }

//    @Override
//    public void updateStatusById(String id, OrderStatus status) {

}
