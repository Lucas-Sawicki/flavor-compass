package org.example.business;

import org.example.business.dao.OrdersDAO;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.domain.Owner;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersServiceMockitoTest {

    @Mock
    private OrdersDAO ordersDAO;

    @InjectMocks
    private OrdersService ordersService;

    @Test
    public void shouldGetOrderByOrderNumberSuccessfully() {
        // given
        Long orderNr = 1L;
        Orders order = OtherFixtures.someOrder();
        when(ordersDAO.getOrderByOrderNumber(orderNr)).thenReturn(order);

        // when
        Orders result = ordersService.getOrderByOrderNumber(orderNr);

        // then
        assertThat(result, is(order));
    }

    @Test
    public void shouldDeleteOrderByOrderNumber() {
        // given
        Long orderNumber = 1L;
        doNothing().when(ordersDAO).delete(orderNumber);

        // when
        ordersService.delete(orderNumber);

        // then
        verify(ordersDAO).delete(orderNumber);
    }

    @Test
    public void shouldSaveOrderSuccessfully() {
        // given
        Orders order = OtherFixtures.someOrder();
        when(ordersDAO.saveOrder(order)).thenReturn(order);

        // when
        Orders result = ordersService.saveOrder(order);

        // then
        assertThat(result, is(order));
    }

    @Test
    public void shouldShowOrdersHistoryForCustomer() {
        // given
        Customer customer =OtherFixtures.someCustomer();
        List<Orders> ordersList = Collections.singletonList(OtherFixtures.someOrder());
        when(ordersDAO.findByCustomer(customer)).thenReturn(ordersList);

        // when
        List<Orders> result = ordersService.ordersHistoryForCustomer(customer);

        // then
        assertThat(result, is(ordersList));
    }

    @Test
    public void shouldShowOrdersHistoryForOwner() {
        // given
        Owner owner = OtherFixtures.someOwner();
        List<Orders> ordersList = Collections.singletonList(OtherFixtures.someOrder());
        when(ordersDAO.findByOwner(owner)).thenReturn(ordersList);

        // when
        List<Orders> result = ordersService.ordersHistoryForOwner(owner);

        // then
        assertThat(result, is(ordersList));
    }

    @Test
    public void shouldCompleteOrderSuccessfully() {
        // given
        Long orderNumber = 1L;
        doNothing().when(ordersDAO).completeOrder(orderNumber);

        // when
        ordersService.completeOrder(orderNumber);

        // then
        verify(ordersDAO).completeOrder(orderNumber);
    }

}