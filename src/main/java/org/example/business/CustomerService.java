package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final RestaurantService restaurantService;

    @Transactional
    public Customer findCustomerByEmail(String email) {
        Optional<User> customer = userDAO.findByEmail(email);
        return customer.get().getCustomer();
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }

    public Customer findCustomerById(Long id) {

        return customerDAO.findCustomerById(id);
    }

    public List<RestaurantDTO> findAllRestaurants() {
        return restaurantService.findAll()
                .stream()
                .map(restaurant -> RestaurantDTO.builder()
                        .restaurantEmail(restaurant.getEmail())
                        .restaurantName(restaurant.getLocalName())
                        .restaurantPhone(restaurant.getPhone())
                        .restaurantWebsite(restaurant.getWebsite())
                        .addressDTO(AddressDTO.builder()
                                .addressCountry(restaurant.getAddress().getCountry())
                                .addressCity(restaurant.getAddress().getCity())
                                .addressStreet(restaurant.getAddress().getStreet())
                                .addressPostalCode(restaurant.getAddress().getPostalCode())
                                .build())
                        .openingHours(restaurant.getOpeningHours().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> OpeningHoursDTO.builder()
                                                .day(entry.getKey())
                                                .openTime(entry.getValue().getOpenTime().toString())
                                                .closeTime(entry.getValue().getCloseTime().toString())
                                                .deliveryStartTime(entry.getValue().getDeliveryStartTime().toString())
                                                .deliveryEndTime(entry.getValue().getDeliveryEndTime().toString())
                                                .build()
                                ))
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }
}
