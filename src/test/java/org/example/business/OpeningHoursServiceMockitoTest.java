package org.example.business;

import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.OpeningHoursMapper;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.repository.OpeningHoursRepository;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpeningHoursServiceMockitoTest {

    @Mock
    private OpeningHoursRepository openingHoursRepository;
    @Mock
    private OpeningHoursMapper openingHoursMapper;

    @InjectMocks
    private OpeningHoursService openingHoursService;

    @Test
    public void shouldGetOpeningHoursForRestaurant() {
        // given
        Restaurant restaurant = OtherFixtures.someRestaurant();
        Map<DayOfWeek, OpeningHours> openingHoursMap = new HashMap<>();
        when(openingHoursRepository.findByRestaurant(restaurant)).thenReturn(openingHoursMap);

        // when
        Map<DayOfWeek, OpeningHours> result = openingHoursService.getOpeningHours(restaurant);

        // then
        assertThat(result, is(openingHoursMap));
    }

    @Test
    public void shouldSaveOpeningHoursCorrectly() {
        // given
        OpeningHours openingHours = OtherFixtures.someOpeningHours();
        when(openingHoursRepository.save(openingHours)).thenReturn(openingHours);

        // when
        OpeningHours result = openingHoursService.saveOpeningHours(openingHours);

        // then
        assertThat(result, is(openingHours));
    }

    @Test
    public void shouldFormatHoursCorrectly() {
        // given
        List<RestaurantDTO> restaurants = new ArrayList<>();
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantName("Test Restaurant");
        restaurantDTO.setOpeningHours(new HashMap<>());
        restaurants.add(restaurantDTO);

        // when
        Map<String, Map<String, String>> result = openingHoursService.formattedHours(restaurants);

        // then
        assertThat(result.containsKey("Test Restaurant"), is(true));
    }

    @Test
    public void shouldUpdateDayAndReturnTrue() {
        // given
        Map<DayOfWeek, OpeningHours> currentOpeningHours = new HashMap<>();
        OpeningHoursDTO openingHoursDTO = new OpeningHoursDTO();
        openingHoursDTO.setDay(DayOfWeek.MONDAY);
        openingHoursDTO.setOpenTime("09:00");
        openingHoursDTO.setCloseTime("17:00");
        OpeningHours openingHours = OtherFixtures.someOpeningHours();
        OpeningHours withOpenTime = openingHours.withOpenTime(LocalTime.of(9, 0));
        OpeningHours openingHoursWithOpenTimeAndCloseTime = withOpenTime.withCloseTime(LocalTime.of(17, 0));


        when(openingHoursMapper.mapFromDto(openingHoursDTO)).thenReturn(openingHoursWithOpenTimeAndCloseTime);

        // when
        boolean result = openingHoursService.updateDay(currentOpeningHours, openingHoursDTO);

        // then
        assertThat(result, is(true));
        assertThat(currentOpeningHours.get(DayOfWeek.MONDAY), is(openingHoursWithOpenTimeAndCloseTime));
    }

    @Test
    public void shouldDeleteByIdCorrectly() {
        // given
        Integer oldId = 1;
        doNothing().when(openingHoursRepository).deleteById(oldId);

        // when
        openingHoursService.deleteById(oldId);

        // then
        verify(openingHoursRepository).deleteById(oldId);
    }
}