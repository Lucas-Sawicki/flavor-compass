package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;


@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    @Transactional
    public Customer findCustomerByEmail(String email) {
        Optional<User> customer = userDAO.findByEmail(email);
        return customer.get().getCustomer();
    }

    @Transactional
    public void createCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    public Customer findCustomerById(Integer id) {
        return customerDAO.findCustomerById(id);
    }

    public List<RestaurantDTO> findAllRestaurants() {
        return restaurantService.findAll()
                .stream()
                .map(restaurantMapper::map)
                .toList();

    }
}
