package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.example.infrastructure.database.repository.jpa.OrdersJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
import org.example.infrastructure.database.repository.mapper.OrdersEntityMapper;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {
    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerEntityMapper customerEntityMapper;
    private final OrdersEntityMapper ordersEntityMapper;
    private final OrdersJpaRepository ordersJpaRepository;


    @Override
    public Optional<Customer> findByEmail(String email) {
        return  customerJpaRepository.findByEmail(email)
                .map(customerEntityMapper::mapFromEntity);
    }

    @Override
    public void saveOrder(Customer customer) {
        List<OrdersEntity> orders = customer.getOrders().stream()
                .map(ordersEntityMapper::mapToEntity)
                .toList();

        orders.forEach(request -> request.setCustomer(customerEntityMapper.mapToEntity(customer)));
        ordersJpaRepository.saveAllAndFlush(orders);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity toSave = customerEntityMapper.mapToEntity(customer);
        CustomerEntity saved = customerJpaRepository.saveAndFlush(toSave);
        return customerEntityMapper.mapFromEntity(saved);
    }

}
