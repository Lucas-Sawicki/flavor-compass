package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.OrdersController;
import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.OrdersDTO;
import org.example.business.*;
import org.example.domain.*;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.hamcrest.Matchers;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrdersController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class OrdersControllerWebMvcTest {
    private final MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private OrdersService ordersService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private UserService userService;
    @MockBean
    private CartService cartService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenPlaceOrder_thenRedirectsToConfirmation() throws Exception {
        UserDetails userDetails = OtherFixtures.someUserDetails();
        Principal principal = new UsernamePasswordAuthenticationToken(userDetails, null);
        Cart cart = new Cart();
        OrderItem orderItem = OtherFixtures.someOrderItem();
        cart.addItem(orderItem);
        Customer customer = OtherFixtures.someCustomer();
        OrdersDTO orderDTO = new OrdersDTO();
        Restaurant restaurant = OtherFixtures.someRestaurant();
        Orders order = OtherFixtures.someOrder();

        when(customerService.findCustomerByEmail(principal.getName())).thenReturn(customer);
        when(cartService.getCart()).thenReturn(cart);
        when(cartService.placeOrder(cart,customer,List.of(restaurant))).thenReturn(List.of(order));

        mockMvc.perform(post("/order/placeOrder")
                        .flashAttr("order", orderDTO)
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/order/confirmation?orderNumbers=20240312222"));
    }

    @Test
    public void whenConfirmation_thenReturnsConfirmation() throws Exception {
        Orders mockOrder = mock(Orders.class);
        Orders order = OtherFixtures.someOrder();
        List<Orders> orders = List.of(order);

        when(ordersService.getOrderByOrderNumber(1L)).thenReturn(order);
        when(mockOrder.getTotalCost()).thenReturn(BigDecimal.TEN);

        mockMvc.perform(get("/order/confirmation")
                        .param("orderNumbers", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmation"))
                .andExpect(model().attribute("orders", orders));
    }

    @Test
    public void whenCancelOrder_thenRedirectsToHistory() throws Exception {
        Orders mockOrder = mock(Orders.class);
        Orders order = OtherFixtures.someOrder();

        when(mockOrder.getOrderDate()).thenReturn(OffsetDateTime.now());
        when(ordersService.getOrderByOrderNumber(1L)).thenReturn(order);

        mockMvc.perform(delete("/order/cancel").param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }
    @Test
    public void whenOrderHistory_thenReturnsOrderHistory() throws Exception {
        UserDetails userDetails = OtherFixtures.someUserDetails();
        Principal principal = new UsernamePasswordAuthenticationToken(userDetails, null);

        Owner owner = OtherFixtures.someOwner();
        User mockUser = mock(User.class);

        when(mockUser.getOwner()).thenReturn(owner);
        when(userService.findByEmail(principal.getName())).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/order/history").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name(Matchers.anyOf(
                        Matchers.is("order_history_by_own"),
                        Matchers.is("order_history_by_cst"))));
    }

    @Test
    public void whenOrderError_thenReturnsOrderError() throws Exception {
        mockMvc.perform(get("/order/order_error"))
                .andExpect(status().isOk())
                .andExpect(view().name("order_error"));
    }

    @Test
    public void whenCompleteOrder_thenRedirectsToHistory() throws Exception {
        mockMvc.perform(patch("/order/complete").param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history?continue"));
    }
}