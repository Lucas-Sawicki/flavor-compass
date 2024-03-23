package org.example.api.controller.mockitoTests;

import org.example.api.controller.CartController;
import org.example.api.dto.OrderItemDTO;
import org.example.business.CartService;
import org.example.business.MenuItemService;
import org.example.domain.Cart;
import org.example.domain.MenuItem;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerMockitoTest {
    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    @Mock
    MenuItemService menuItemService;

    @Mock
    Model model;

    @Test
    public void whenAddToCartThenReturnsToCartPage() {
        //given
        MenuItem menuItem = OtherFixtures.someMenuItem();
        OrderItemDTO orderItemDTO = OtherFixtures.someOrderItemDTO();
        //when
        when(menuItemService.findById(orderItemDTO.getMenuItemId())).thenReturn(menuItem);
        String viewName = cartController.addToCart(orderItemDTO.getMenuItemId(),orderItemDTO.getQuantity());
        //then
        Assertions.assertEquals("redirect:/cart", viewName);
        verify(cartService, times(1)).addToCart(orderItemDTO);
    }

    @Test
    public void whenRemoveFromCartThenBackToCartPage() {
        //given
        OrderItemDTO orderItemDTO = OtherFixtures.someOrderItemDTO();
        //when
        String viewName = cartController.removeFromCart(orderItemDTO, new MockHttpSession());
        //then
        Assertions.assertEquals("redirect:/cart", viewName);
        verify(cartService, times(1)).removeFromCart(orderItemDTO);
    }

    @Test
    public void whenShowCartThenReturnsCartPage() {
        //given
        Cart cart = new Cart();
        //when
        when(cartService.getCart()).thenReturn(cart);
        String viewName = cartController.showCart(model);
        //then
        Assertions.assertEquals("cart", viewName);
        verify(model, times(1)).addAttribute("cart", cart);
    }
    @Test
    public void whenAddToCartThenThrowsException() {
        //given
        Integer menuItemId = 1;
        Integer quantity = 2;
        //when
        when(menuItemService.findById(menuItemId)).thenThrow(new RuntimeException());
        //then
        assertThrows(ResponseStatusException.class, () -> {
            cartController.addToCart(menuItemId, quantity);
        });
    }

    @Test
    public void whenRemoveFromCartThenThrowsException() {
        //given
        OrderItemDTO orderItemDTO = OtherFixtures.someOrderItemDTO();
        //when
        doThrow(new RuntimeException()).when(cartService).removeFromCart(orderItemDTO);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            cartController.removeFromCart(orderItemDTO, new MockHttpSession());
        });
    }

    @Test
    public void whenShowCartThenThrowsException() {
        //given, when
        when(cartService.getCart()).thenThrow(new RuntimeException());
        //then
        assertThrows(ResponseStatusException.class, () -> {
            cartController.showCart(model);
        });
    }

}
