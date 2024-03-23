package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.CustomerController;
import org.example.business.CustomerService;
import org.example.business.TokenService;
import org.example.domain.Customer;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class CustomerControllerWebMvcTest {


    private final MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenGetCustomerByIdThenReturnsCustomerPage() throws Exception {
        Integer customerId = 1;
        Customer customer = OtherFixtures.someCustomer();

        when(customerService.findCustomerById(customerId)).thenReturn(customer);

        mockMvc.perform(get("/customer/{customerID}", customerId))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_portal"))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    public void whenGetCustomerThenReturnsCustomerPage() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_portal"));
    }
}