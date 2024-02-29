package org.example.business.dao;

import org.example.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDAO {

    Customer saveCustomer(Customer customer);


    Customer findCustomerById(Long id);
}
