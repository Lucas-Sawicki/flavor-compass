package org.example.api.controller.webMvcTests;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.api.controller.DeliveryController;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.mapper.DeliveryMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.TokenService;
import org.example.business.teryt.LoadDataService;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = DeliveryController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class DeliveryControllerWebMvcTest {

    private final MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private LoadDataService loadDataService;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private DeliveryRangeService deliveryRangeService;
    @MockBean
    private DeliveryMapper deliveryMapper;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenGetDeliveryThenReturnsDeliveryPage() throws Exception {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Principal principal = new UsernamePasswordAuthenticationToken(userDetails, null);
        org.example.domain.User user = OtherFixtures.someUser1();
        Owner owner = OtherFixtures.someOwner();
        org.example.domain.User withOwner = user.withOwner(owner);
        Restaurant restaurant = OtherFixtures.someRestaurant();

        when(ownerService.findOwnerByUser(user.getEmail())).thenReturn(withOwner.getOwner());
        when(restaurantService.findRestaurantsByOwnerId(withOwner.getOwner().getOwnerId())).thenReturn(List.of(restaurant));

        mockMvc.perform(get("/owner/delivery").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("delivery_range"));
    }

    @Test
    public void whenPostAddStreetsThenRedirectsToSuccessRegisterPage() throws Exception {
        DeliveryRangeDTO deliveryRangeDTO = new DeliveryRangeDTO();

        when(deliveryMapper.map(any(), any(), any())).thenReturn(deliveryRangeDTO);

        mockMvc.perform(post("/owner/delivery/addStreets")
                        .param("cit", "someCity")
                        .param("restaurant", "someRestaurant")
                        .param("approvedNames", "someStreet"))
                .andExpect(status().isOk())
                .andExpect(view().name("success_register"));
    }
}