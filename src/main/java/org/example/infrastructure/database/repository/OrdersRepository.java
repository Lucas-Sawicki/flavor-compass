package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OrdersDAO;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.domain.Owner;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.repository.jpa.OrdersJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
import org.example.infrastructure.database.repository.mapper.OrdersEntityMapper;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrdersRepository implements OrdersDAO {

    private final OrdersJpaRepository ordersJpaRepository;
    private final OrdersEntityMapper ordersEntityMapper;
    private final CustomerEntityMapper customerEntityMapper;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public Orders getOrderByOrderNumber(Long orderNumber) {
        OrdersEntity entity = ordersJpaRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        return ordersEntityMapper.mapFromEntity(entity);
    }


    @Override
    public Orders saveOrder(Orders orders) {
        OrdersEntity ordersEntity = ordersEntityMapper.mapToEntity(orders);
        OrdersEntity saved = ordersJpaRepository.saveAndFlush(ordersEntity);
        return ordersEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void delete(Long orderNumber) {
        ordersJpaRepository.deleteByOrderNumber(orderNumber);
    }

    @Override
    public List<Orders> findByCustomer(Customer customer) {
        CustomerEntity customerEntity = customerEntityMapper.mapToEntity(customer);
        List<OrdersEntity> byCustomer = ordersJpaRepository.findByCustomer(customerEntity);
        return byCustomer.stream()
                .map(ordersEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Orders> findByOwner(Owner owner) {
        OwnerEntity ownerEntity = ownerEntityMapper.mapToEntity(owner);
        List<OrdersEntity> byOwner = ordersJpaRepository.findByOwner(ownerEntity);
        return byOwner.stream()
                .map(ordersEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void completeOrder(Long orderNumber) {
        Orders order = getOrderByOrderNumber(orderNumber);
        OrdersEntity ordersEntity = ordersEntityMapper.mapToEntity(order);
        ordersEntity.setStatus("COMPLETED");
        ordersJpaRepository.save(ordersEntity);
    }

}
