package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.SearchController;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.OpeningHoursService;
import org.example.business.RestaurantService;
import org.example.business.TokenService;
import org.example.domain.Restaurant;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SearchController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class SearchControllerWebMvcTestTest {

    private final MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantMapper restaurantMapper;
    @MockBean
    private OpeningHoursService openingHoursService;
    @MockBean
    private DeliveryRangeService deliveryRangeService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testListOfRestaurants() throws Exception {
        DeliveryRangeDTO deliveryRangeDTO = OtherFixtures.someDeliveryRangeDTO();
        deliveryRangeDTO.setRestaurant("Restaurant Example");
        Restaurant restaurant = OtherFixtures.someRestaurant();
        List<Restaurant> restaurantList = List.of(restaurant);
        RestaurantDTO restaurantDTO = OtherFixtures.someRestaurantDTO();
        Page<Restaurant> restaurants = new PageImpl<>(restaurantList);

        List<RestaurantDTO> restaurantDTOS = List.of(restaurantDTO);

        Map<String, Map<String, String>> restaurantGroupedHours = new HashMap<>();

        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);
        when(deliveryRangeService.processingFindRestaurants(deliveryRangeDTO)).thenReturn(restaurantList);
        when(restaurantService.pagination(anyInt(), anyInt(), anyString(), anyList())).thenReturn(restaurants);
        when(openingHoursService.formattedHours(restaurantDTOS)).thenReturn(restaurantGroupedHours);

        mockMvc.perform(post("/find")
                        .flashAttr("deliveryRangeDTO", deliveryRangeDTO)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("page", "0")
                        .param("size", "7")
                        .param("sortBy", "localName")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("find_restaurants"))
                .andExpect(model().attributeExists("restaurants", "pagination", "restaurantGroupedHours"))
                .andExpect(model().attribute("restaurants", restaurantDTOS))
                .andExpect(model().attribute("pagination", restaurants))
                .andExpect(model().attribute("restaurantGroupedHours", restaurantGroupedHours));
    }
}