package org.example.business;

import org.example.api.dto.DeliveryRangeDTO;
import org.example.business.dao.DeliverRangeDAO;
import org.example.domain.DeliveryRange;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.example.infrastructure.database.repository.mapper.DeliveryRangeEntityMapper;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryRangeServiceMockitoTest {

    @Mock
    private DeliverRangeDAO deliverRangeDAO;
    @Mock
    private DeliveryRangeEntityMapper deliveryRangeMapper;
    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private DeliveryRangeService deliveryRangeService;

    @Test
    public void shouldReturnEmailFromString() {
        // given
        String input = "Hello, my email is test@example.com";

        // when
        String result = DeliveryRangeService.extractEmail(input);

        // then
        assertThat(result, is("test@example.com"));
    }

    @Test
    public void shouldAddDeliveryPlacesCorrectly() {
        // given
        DeliveryRangeDTO deliveryRangeDTO = OtherFixtures.someDeliveryRangeDTO();
        deliveryRangeDTO.setRestaurant("Restaurant (test@example.com)");
        deliveryRangeDTO.setStreets(Collections.singletonList("Street"));

        Restaurant restaurant = OtherFixtures.someRestaurant();
        when(restaurantService.findRestaurantsByEmail("test@example.com")).thenReturn(restaurant);
        doNothing().when(deliverRangeDAO).saveDeliverRange(any(DeliveryRange.class));

        // when
        deliveryRangeService.addDeliveryPlaces(deliveryRangeDTO);

        // then
        verify(deliverRangeDAO).saveDeliverRange(any(DeliveryRange.class));
    }

    @Test
    public void shouldReturnRestaurantsWhenInputCityAndStreet() {
        // given
        DeliveryRangeDTO deliveryRangeDTO = OtherFixtures.someDeliveryRangeDTO();

        DeliveryRangeEntity deliveryRangeEntity = new DeliveryRangeEntity();
        DeliveryRange deliveryRange = OtherFixtures.someDeliveryRange();
        List<Restaurant> restaurants = Collections.singletonList(OtherFixtures.someRestaurant());
        DeliveryRange withRestaurants = deliveryRange.withRestaurants(restaurants);
        when(deliverRangeDAO.findByCityAndStreet("city", "ul. street")).thenReturn(Optional.of(deliveryRangeEntity));
        when(deliveryRangeMapper.mapFromEntity(deliveryRangeEntity)).thenReturn(withRestaurants);

        // when
        List<Restaurant> result = deliveryRangeService.processingFindRestaurants(deliveryRangeDTO);

        // then
        assertThat(result, is(restaurants));
    }

}