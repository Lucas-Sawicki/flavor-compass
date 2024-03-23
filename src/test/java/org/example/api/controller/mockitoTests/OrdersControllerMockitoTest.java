package org.example.api.controller.mockitoTests;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.api.controller.OrdersController;
import org.example.api.dto.OrdersDTO;
import org.example.business.CartService;
import org.example.business.CustomerService;
import org.example.business.OrdersService;
import org.example.business.UserService;
import org.example.domain.*;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.repository.mapper.OrdersEntityMapper;
import org.example.util.EntityFixtures;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdersControllerMockitoTest {

    @Mock
    private OrdersService ordersService;

    @Mock
    private CustomerService customerService;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private Model model;
    @Mock
    private HttpSession session;

    @Mock
    private Principal principal;


    @Test
    public void shouldPlaceOrderWithNormalFlow() {
        // given
        when(principal.getName()).thenReturn("email@example.com");
        Customer customer = OtherFixtures.someCustomer();
        when(customerService.findCustomerByEmail("email@example.com")).thenReturn(customer);
        Cart cart = new Cart();
        when(cartService.getCart()).thenReturn(cart);
        Orders order = OtherFixtures.someOrder();

        when(cartService.placeOrder(cart, customer, new ArrayList<>())).thenReturn(List.of(order));

        // when
        String viewName = ordersController.placeOrder(new OrdersDTO(), null, session, principal);

        // then
        assertEquals("redirect:/order/confirmation?orderNumbers=20240312222", viewName);
    }

    @Test
    public void shouldRedirectToLogin() {
        // given
        Cart cart = new Cart();

        when(principal.getName()).thenReturn("email@example.com");
        when(customerService.findCustomerByEmail("email@example.com")).thenReturn(null);
        when(cartService.getCart()).thenReturn(cart);
        // when
        String viewName = ordersController.placeOrder(new OrdersDTO(), null, session, principal);

        // then
        assertEquals("redirect:/auth/login", viewName);
    }

    @Test
    public void shouldConfirmSuccessfully() {
        // given
        Orders order = OtherFixtures.someOrder();

        when(ordersService.getOrderByOrderNumber(20240312222L)).thenReturn(order);

        // when
        String viewName = ordersController.confirmation("20240312222", model);

        // then
        assertEquals("confirmation", viewName);
        verify(model).addAttribute("orders", Collections.singletonList(order));
    }

    @Test
    public void shouldThrowExceptionWhenProcessingOrder() {
        // given
        when(ordersService.getOrderByOrderNumber(1L)).thenThrow(new RuntimeException());

        // when and then
        assertThrows(ResponseStatusException.class, () -> ordersController.confirmation("1", model));
    }

    @Test
    public void shouldCancelOrderCorrectly() {
        // given
        Orders order = OtherFixtures.someOrder();

        order.withOrderDate(OffsetDateTime.now().minusMinutes(10));
        when(ordersService.getOrderByOrderNumber(20240312222L)).thenReturn(order);

        // when
        String viewName = ordersController.cancelOrder(20240312222L);

        // then
        assertEquals("redirect:/order/history", viewName);
        verify(ordersService).delete(20240312222L);
    }

    @Test
    public void  shouldRedirectToErrorPageWhenPassedTooMuchTime() {
        // given
        Orders order = OtherFixtures.someOrder();
        Orders withOrderDate = order.withOrderDate(OffsetDateTime.now().minusMinutes(30));
        when(ordersService.getOrderByOrderNumber(1L)).thenReturn(withOrderDate);

        // when
        String viewName = ordersController.cancelOrder(1L);

        // then
        assertEquals("redirect:/order/order_error", viewName);
    }

    @Test
    public void shouldThrowExceptionWhenTryCancelOrder() {
        // given
        when(ordersService.getOrderByOrderNumber(1L)).thenThrow(new RuntimeException());

        // when and then
        assertThrows(ResponseStatusException.class, () -> ordersController.cancelOrder(1L));
    }

//    @Test
//    public void shouldShowCheckoutPage() {
//        // given
//        Cart cart = new Cart();
//        when(cartService.getCart()).thenReturn(cart);
//
//        // when
//        String viewName = ordersController.checkout(model);
//
//        // then
//        assertEquals("checkout", viewName);
//        verify(model).addAttribute("order", new OrdersDTO());
//        verify(model).addAttribute("cart", cart);
//    }

//    @Test
//    public void shouldRedirectWhenCartIsNull() {
//        // given
//        when(cartService.getCart()).thenReturn(null);
//
//        // when
//        String viewName = ordersController.checkout(model);
//
//        // then
//        assertEquals("redirect:/cart", viewName);
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenGetCart() {
//        // given
//        when(cartService.getCart()).thenThrow(new RuntimeException());
//
//        // when, then
//        assertThrows(ResponseStatusException.class, () -> ordersController.checkout(model));
//    }

    @Test
    public void shouldShowOrderHistoryForOwner() {
        // given
        when(principal.getName()).thenReturn("john@example.com");

        Owner owner = OtherFixtures.someOwner();
        User user = owner.getUser();
        User withOwner = user.withOwner(owner);
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(withOwner));

        // when
        String viewName = ordersController.orderHistory(model, principal);

        // then
        assertEquals("order_history_by_own", viewName);
        verify(userService).findByEmail("john@example.com");
    }

    @Test
    public void shouldShowOrderHistoryForCustomer() {
        // given
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("alice@example.com");

        Customer customer = OtherFixtures.someCustomer();
        User user = customer.getUser();
        User withCustomer = user.withCustomer(customer);

        when(userService.findByEmail("alice@example.com")).thenReturn(Optional.of(withCustomer));

        // when
        String viewName = ordersController.orderHistory(model, principal);

        // then
        assertEquals("order_history_by_cst", viewName);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // given
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("email@example.com");

        when(userService.findByEmail("email@example.com")).thenReturn(Optional.empty());

        // when, then
        assertThrows(ResponseStatusException.class, () -> ordersController.orderHistory(model, principal));
    }
}
