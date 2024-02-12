package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.domain.Customer;
import org.example.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {
    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> findByEmail(String email) {
        return  customerJpaRepository.findByEmail(email)
                .map(customerEntityMapper::mapFromEntity);
    }

}
