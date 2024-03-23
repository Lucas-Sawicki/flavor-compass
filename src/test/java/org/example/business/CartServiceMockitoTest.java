package org.example.business;

import jakarta.servlet.http.HttpSession;
import org.example.api.dto.OrderItemDTO;
import org.example.domain.*;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceMockitoTest {

    @Mock
    private HttpSession httpSession;
    @Mock
    private MenuItemService menuItemService;
    @Mock
    private OrdersService ordersService;
    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private CartService cartService;

    @Test
    public void shouldReturnNewCart() {
        // given
        when(httpSession.getAttribute("cart")).thenReturn(null);

        // when
        Cart result = cartService.getCart();
        // then
        assertThat(result, is(notNullValue()));
        verify(httpSession).setAttribute(eq("cart"), any(Cart.class));
    }

    @Test
    public void shouldAddToCartSuccessfully() {
        // given
        OrderItemDTO orderItemDTO = OtherFixtures.someOrderItemDTO();

        Cart cart = new Cart();
        MenuItem menuItem = OtherFixtures.someMenuItem();

        when(httpSession.getAttribute("cart")).thenReturn(cart);
        when(menuItemService.findById(orderItemDTO.getMenuItemId())).thenReturn(menuItem);

        // when
        cartService.addToCart(orderItemDTO);

        // then
        assertThat(cart.getItems(), is(not(empty())));
        assertThat(cart.getItems().get(0).getMenuItem(), is(menuItem));
    }
    @Test
    public void shouldRemoveFromCartSuccessfully() {
        // given
        OrderItemDTO orderItemDTO = OtherFixtures.someOrderItemDTO();

        Cart cart = mock(Cart.class);
        List<OrderItem> items = new ArrayList<>();
        OrderItem orderItem = OtherFixtures.someOrderItem();
        items.add(orderItem);
        when(cart.getItems()).thenReturn(items);

        when(httpSession.getAttribute("cart")).thenReturn(cart);

        // when
        cartService.removeFromCart(orderItemDTO);

        // then
        assertThat(items, is(Collections.emptyList()));
    }

    @Test
    public void shouldPlaceOrderCorrectly() {
        // given
        Cart cart = new Cart();
        Customer customer = OtherFixtures.someCustomer();
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = OtherFixtures.someRestaurant();
        restaurants.add(restaurant);

        when(ordersService.saveOrder(any(Orders.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        List<Orders> result = cartService.placeOrder(cart, customer, restaurants);

        // then
        assertThat(result, is(not(empty())));
        assertThat(result.get(0).getRestaurant(), is(restaurant));
        verify(httpSession).removeAttribute("cart");
    }
}