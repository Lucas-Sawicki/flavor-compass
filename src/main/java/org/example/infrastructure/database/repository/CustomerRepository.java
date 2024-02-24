package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.domain.Customer;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {
    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity toSave = customerEntityMapper.mapToEntity(customer);
        CustomerEntity saved = customerJpaRepository.saveAndFlush(toSave);
        return customerEntityMapper.mapFromEntity(saved);
    }
}
