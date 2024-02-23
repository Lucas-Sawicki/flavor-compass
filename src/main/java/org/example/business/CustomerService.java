package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.api.dto.mapper.UserMapper;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public Customer saveCustomer(Customer customer) {
      return customerDAO.saveCustomer(customer);
    }
}
