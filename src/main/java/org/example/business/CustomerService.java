package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;

    @Transactional
    public Customer findCustomer(String email) {
        Optional<User> customer = userDAO.findByEmail(email);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by email: [%s]".formatted(email));
        }
        return customer.get().getCustomer();
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
      return customerDAO.saveCustomer(customer);
    }

    public Customer findCustomerById(Long id) {
        Optional<User> customer = userDAO.findOwnerById(id);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by id: [%s]".formatted(id));
        }
        return customer.get().getCustomer();
    }
}
