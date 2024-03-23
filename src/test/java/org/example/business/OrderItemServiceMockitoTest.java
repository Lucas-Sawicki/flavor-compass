package org.example.business;

import org.example.business.dao.OrderItemDAO;
import org.example.domain.OrderItem;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceMockitoTest {

    @Mock
    private OrderItemDAO orderItemDAO;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void shouldSaveOrderItemSuccessfully() {
        // given
        OrderItem orderItem = OtherFixtures.someOrderItem();
        doNothing().when(orderItemDAO).save(orderItem);

        // when
        orderItemService.save(orderItem);

        // then
        verify(orderItemDAO).save(orderItem);
    }

}