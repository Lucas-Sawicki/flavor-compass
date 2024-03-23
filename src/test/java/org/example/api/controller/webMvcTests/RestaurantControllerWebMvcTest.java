package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.RestaurantController;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.api.dto.mapper.RestaurantMapper;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.TokenService;
import org.example.domain.MenuItem;
import org.example.domain.Owner;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class RestaurantControllerWebMvcTest {

    private final MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private MenuItemService menuItemService;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private RestaurantMapper restaurantMapper;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testAddRestaurant() throws Exception {
        UserDetails userDetails = OtherFixtures.someUserDetails();
        Principal principal = new UsernamePasswordAuthenticationToken(userDetails, null);
        Owner owner = OtherFixtures.someOwner();
        RestaurantDTO restaurantDTO = OtherFixtures.someRestaurantDTO();
        Restaurant restaurant = OtherFixtures.someRestaurant();

        when(restaurantService.existsByEmail(anyString())).thenReturn(false);
        when(ownerService.findOwnerByUser(principal.getName())).thenReturn(owner);
        when(restaurantMapper.map(restaurantDTO, restaurantDTO.getAddressDTO(), owner)).thenReturn(restaurant);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", restaurantDTO.getId());
        params.add("restaurantName", restaurantDTO.getRestaurantName());
        params.add("restaurantWebsite", restaurantDTO.getRestaurantWebsite());
        params.add("restaurantPhone", restaurantDTO.getRestaurantPhone());
        params.add("restaurantEmail", restaurantDTO.getRestaurantEmail());
        mockMvc.perform(post("/owner/add/restaurant")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params)
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/auth/success_register"));
    }
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/owner/add/restaurant"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_restaurant"))
                .andExpect(model().attributeExists("restaurantDTO", "openingHoursDTO", "openingHours"));
    }

    @Test
    public void testShowMenuItems() throws Exception {
        RestaurantDTO restaurantDTO = OtherFixtures.someRestaurantDTO();

        List<MenuItemDTO> menuItems = List.of(OtherFixtures.someMenuItemDTO());

        Page<MenuItem> menu = new PageImpl<>(new ArrayList<>());

        when(restaurantService.findRestaurantById(anyInt())).thenReturn(restaurantDTO);
        when(menuItemService.findMenuByRestaurant(anyInt())).thenReturn(menuItems);
        when(menuItemService.pagination(anyInt(), anyInt(), anyString(), anyInt())).thenReturn(menu);

        mockMvc.perform(get("/restaurant/{id}", 1)
                        .param("page", "0")
                        .param("size", "7")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant_portal"))
                .andExpect(model().attributeExists("pagination", "orderItemDTO", "restaurantDTO", "menuItems"))
                .andExpect(model().attribute("restaurantDTO", restaurantDTO))
                .andExpect(model().attribute("menuItems", menuItems));
    }

}