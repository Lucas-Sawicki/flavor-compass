package org.example.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.domain.exception.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;

    @Transactional
    public Customer findCustomerByEmail(String email) {
        User customer = userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return customer.getCustomer();
    }

    @Transactional
    public void createCustomer(Customer customer) {
        try {
            customerDAO.saveCustomer(customer);
        } catch (DataAccessException ex) {
            throw new CustomException("Error accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public Customer findCustomerById(Integer id) {
        try {
            return customerDAO.findCustomerById(id);
        } catch (EntityNotFoundException ex) {
            throw new CustomException("Customer not found.", ex.getMessage());
        }
    }

}
