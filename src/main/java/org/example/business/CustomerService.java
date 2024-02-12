package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.domain.Customer;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;


    @Transactional
    public Customer findCustomer(String email) {
        Optional<Customer> customer = customerDAO.findByEmail(email);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by email: [%s]".formatted(email));
        }
        return customer.get();
    }
    @Transactional
    public void saveOrder(Customer customer) {
        customerDAO.saveOrder(customer);
    }
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }
}
