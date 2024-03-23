package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.MenuItemController;
import org.example.api.dto.MenuItemDTO;
import org.example.business.MenuItemService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.TokenService;
import org.example.domain.Owner;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MenuItemController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class MenuItemControllerWebMvcTest {

    private final MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private MenuItemService menuItemService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenAddMenuPage_thenReturnsAddMenu() throws Exception {
        UserDetails userDetails = OtherFixtures.someUserDetails();
        String email = userDetails.getUsername();
        Principal principal = new UsernamePasswordAuthenticationToken(userDetails, null);
        Owner owner = OtherFixtures.someOwner();

        when(ownerService.findOwnerByUser(email)).thenReturn(owner);


        mockMvc.perform(get(MenuItemController.ADD_MENU).principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("add_menu"));
    }

    @Test
    public void whenAddMenu_thenRedirectsToSuccessRegister() throws Exception {
        MenuItemDTO menuItemDTO = OtherFixtures.someMenuItemDTO();
        mockMvc.perform(post(MenuItemController.ADD_MENU)
                        .flashAttr("menuItemDTO", menuItemDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/success_register"));
    }
}