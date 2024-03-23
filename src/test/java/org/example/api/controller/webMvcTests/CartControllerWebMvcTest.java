package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.CartController;
import org.example.api.dto.OrderItemDTO;
import org.example.business.CartService;
import org.example.business.MenuItemService;
import org.example.business.TokenService;
import org.example.domain.Cart;
import org.example.domain.MenuItem;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class CartControllerWebMvcTest {

    private final MockMvc mockMvc;

    @MockBean
    private CartService cartService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private MenuItemService menuItemService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void whenGetCartThenReturnsCartPage() throws Exception {
        Cart cart = new Cart();
        when(cartService.getCart()).thenReturn(cart);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("cart", cart));
    }

    @Test
    public void whenPostAddToCartWithValidDataThenRedirectsToCartPage() throws Exception {
        MenuItem menuItem = OtherFixtures.someMenuItem();

        when(menuItemService.findById(1)).thenReturn(menuItem);

        mockMvc.perform(post("/add-to-cart")
                        .param("id", "1")
                        .param("quantity", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void whenPostRemoveFromCartWithValidDataThenRedirectsToCartPage() throws Exception {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setMenuItemId(1);
        orderItemDTO.setQuantity(1);

        mockMvc.perform(post("/cart/remove")
                        .flashAttr("orderItemDTO", orderItemDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}