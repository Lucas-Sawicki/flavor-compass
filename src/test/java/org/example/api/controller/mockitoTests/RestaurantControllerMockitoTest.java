package org.example.api.controller.mockitoTests;

import org.example.api.controller.RestaurantController;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.OrderItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.MenuItem;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantControllerMockitoTest {
    @Mock
    private RestaurantService restaurantService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private BindingResult bindingResult;
    @Mock
    private RestaurantMapper restaurantMapper;

    @Test
    public void shouldShowHomePageCorrectly() {
        // given, when
        String viewName = restaurantController.homePage(model);

        // then
        assertEquals("add_restaurant", viewName);
        verify(model).addAttribute(eq("restaurantDTO"), isA(RestaurantDTO.class));
        verify(model).addAttribute(eq("openingHoursDTO"), isA(OpeningHoursDTO.class));
        verify(model).addAttribute(eq("openingHours"), isA(Map.class));
    }

    @Test
    public void shouldAddRestaurantSuccessfully() {
        // given
        when(principal.getName()).thenReturn("john@example.com");
        Owner owner = OtherFixtures.someOwner();
        when(ownerService.findOwnerByUser("john@example.com")).thenReturn(owner);
        when(restaurantService.existsByEmail("local@contact.com")).thenReturn(false);
        RestaurantDTO restaurant = OtherFixtures.someRestaurantDTO();
        Restaurant someRestaurant = OtherFixtures.someRestaurant();
        when(restaurantMapper.map(restaurant, restaurant.getAddressDTO(), owner)).thenReturn(someRestaurant);

        // when
        ModelAndView modelAndView = restaurantController.addRestaurant(restaurant, principal, bindingResult);

        // then
        assertEquals("redirect:/auth/success_register", modelAndView.getViewName());
    }

    @Test
    public void shouldNotAddRestaurantWhenEmailIsTaken() {
        // given
        when(restaurantService.existsByEmail("local@contact.com")).thenReturn(true);
        RestaurantDTO restaurant = OtherFixtures.someRestaurantDTO();

        // when
        ModelAndView modelAndView = restaurantController.addRestaurant(restaurant, principal, bindingResult);

        // then
        assertEquals("Email is already taken", modelAndView.getViewName());
    }

    @Test
    public void shouldShowMenuItemsCorrectly() {
        // given
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        when(restaurantService.findRestaurantById(1)).thenReturn(restaurantDTO);
        Page<MenuItem> menu = new PageImpl<>(new ArrayList<>());
        when(menuItemService.pagination(0, 7, "name", 1)).thenReturn(menu);

        // when
        String viewName = restaurantController.showMenuItems(1, 0, 7, "name", model);

        // then
        assertEquals("restaurant_portal", viewName);
        verify(model).addAttribute("pagination", menu);
        verify(model).addAttribute("orderItemDTO", new OrderItemDTO());
        verify(model).addAttribute("restaurantDTO", restaurantDTO);
        verify(model).addAttribute("menuItems", new ArrayList<>());
    }

}
