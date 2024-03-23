package org.example.business;

import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.dao.RestaurantDAO;
import org.example.domain.Address;
import org.example.domain.OpeningHours;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class RestaurantServiceMockitoTest {

    @Mock
    private RestaurantDAO restaurantDAO;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    private OwnerService ownerService;

    @Mock
    private OpeningHoursService openingHoursService;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void shouldAddRestaurantSuccessfully() {
        // given
        Restaurant restaurant = OtherFixtures.someRestaurant();
        Map<DayOfWeek, OpeningHours> openingHours = new TreeMap<>();
        OpeningHours hours = OtherFixtures.someOpeningHours();
        openingHours.put(DayOfWeek.MONDAY, hours);
        Restaurant withOpeningHours = restaurant.withOpeningHours(openingHours);

        when(openingHoursService.saveOpeningHours(any(OpeningHours.class))).thenReturn(hours);
        when(restaurantDAO.saveRestaurant(any(Restaurant.class))).thenReturn(withOpeningHours);

        // when
        restaurantService.addRestaurant(withOpeningHours);

        // then
        verify(openingHoursService, times(1)).saveOpeningHours(hours);
        verify(restaurantDAO, times(1)).saveRestaurant(withOpeningHours);
    }

    @Test
    public void shouldSaveRestaurantCorrectly() {
        // given
        Restaurant restaurant = OtherFixtures.someRestaurant();

        // when
        restaurantService.saveRestaurant(restaurant);

        // then
        verify(restaurantDAO, times(1)).saveRestaurant(restaurant);
    }

    @Test
    public void shouldCheckIfEmailExistsCorrectly() {
        // given
        String email = "test@test.com";
        when(restaurantDAO.existsByEmail(email)).thenReturn(true);

        // when
        Boolean exists = restaurantService.existsByEmail(email);

        // then
        assertThat("Email should exist", exists, is(true));
        verify(restaurantDAO, times(1)).existsByEmail(email);
    }

    @Test
    public void shouldFindRestaurantsByOwnerId() {
        // given
        Integer ownerId = 1;
        Owner owner = OtherFixtures.someOwner();
        List<Restaurant> restaurants = new ArrayList<>();
        when(ownerService.findOwnerById(ownerId)).thenReturn(owner);
        when(restaurantDAO.findAvailableRestaurantsByOwner(owner)).thenReturn(restaurants);

        // when
        List<Restaurant> result = restaurantService.findRestaurantsByOwnerId(ownerId);

        // then
        assertEquals(restaurants, result);
        verify(ownerService, times(1)).findOwnerById(ownerId);
        verify(restaurantDAO, times(1)).findAvailableRestaurantsByOwner(owner);
    }
    @Test
    public void shouldFindRestaurantsByEmail() {
        // given
        String email = "test@test.com";
        Restaurant restaurant = OtherFixtures.someRestaurant();
        when(restaurantDAO.findRestaurantsByEmail(email)).thenReturn(restaurant);

        // when
        Restaurant result = restaurantService.findRestaurantsByEmail(email);

        // then
        assertThat("Restaurant should be found", result, is(restaurant));
        verify(restaurantDAO, times(1)).findRestaurantsByEmail(email);
    }

    @Test
    public void shouldFindEmailFromString() {
        // given
        String restaurant = "email=test@test.com";
        String expectedEmail = "test@test.com";

        // when
        String email = restaurantService.findEmailFromString(restaurant);

        // then
        assertThat("Email should be found", email, is(expectedEmail));
    }

    @Test
    public void shouldFindRestaurantById() {
        // given
        Integer id = 1;
        Restaurant restaurant = OtherFixtures.someRestaurant();
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        when(restaurantDAO.findRestaurantById(id)).thenReturn(restaurant);
        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);

        // when
        RestaurantDTO result = restaurantService.findRestaurantById(id);

        // then
        assertThat("RestaurantDTO should be found", result, is(restaurantDTO));
        verify(restaurantDAO, times(1)).findRestaurantById(id);
        verify(restaurantMapper, times(1)).map(restaurant);
    }

    @Test
    public void shouldPaginationCorrectly() {
        // given
        int page = 0;
        int size = 10;
        String sortBy = "name";
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            restaurants.add(OtherFixtures.someRestaurant());
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Restaurant> expectedPage = new PageImpl<>(restaurants.subList(0, 10), pageRequest, restaurants.size());

        // when
        Page<Restaurant> result = restaurantService.pagination(page, size, sortBy, restaurants);

        // then
        assertThat("Page should be returned", result, is(expectedPage));
    }

    @Test
    public void shouldUpdateAddress() {
        // given
        Address address = OtherFixtures.someAddress();
        Restaurant restaurant = OtherFixtures.someRestaurant();
        Restaurant withAddress = restaurant.withAddress(address);

        // when
        restaurantService.updateAddress(address, withAddress);

        // then
        verify(restaurantDAO, times(1)).saveRestaurant(withAddress);
    }

}