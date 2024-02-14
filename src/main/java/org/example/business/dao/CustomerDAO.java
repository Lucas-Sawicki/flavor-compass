package org.example.business.dao;

import org.example.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDAO {

    Optional<Customer> findByEmail(String email);

    void saveOrder(Customer customer);

    void saveCustomer(Customer customer);
}
