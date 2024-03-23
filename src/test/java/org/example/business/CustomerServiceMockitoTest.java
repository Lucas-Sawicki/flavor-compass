package org.example.business;

import org.example.business.dao.CustomerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Customer;
import org.example.domain.User;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceMockitoTest {
    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void shouldReturnCustomerFromEmail() {
        // given
        String email = "alice@example.com";
        Customer customer = OtherFixtures.someCustomer();
        User user = customer.getUser();
        User withCustomer = user.withCustomer(customer);
        when(userDAO.findByEmail(email)).thenReturn(Optional.of(withCustomer));

        // when
        Customer result = customerService.findCustomerByEmail(email);

        // then
        assertThat(result, is(customer));
    }

    @Test
    public void shouldReturnCustomerFromId() {
        // given
        Integer id = 1;
        Customer customer = OtherFixtures.someCustomer();
        when(customerDAO.findCustomerById(id)).thenReturn(customer);

        // when
        Customer result = customerService.findCustomerById(id);

        // then
        assertThat(result, is(customer));
    }

}