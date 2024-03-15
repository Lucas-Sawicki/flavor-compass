package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import org.example.api.dto.MenuItemDTO;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.domain.Restaurant;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;


import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemControllerMockitoTest {


    @InjectMocks
    MenuItemController menuItemController;

    @Mock
    RestaurantService restaurantService;

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @Mock
    HttpSession session;

    @Mock
    Principal principal;

    @Mock
    BindingResult bindingResult;

    @Test
    public void shouldAddMenuPageThenThrowException() {
        //given
        when(principal.getName()).thenReturn("test@test.com");
        when(ownerService.findOwnerByUser(anyString())).thenThrow(new RuntimeException());

        //when, then
        assertThrows(ResponseStatusException.class, () -> {
            menuItemController.addMenuPage(model, session, principal);
        });
    }

    @Test
    public void  shouldAddMenuPageCorrectly() {
        //given
        when(principal.getName()).thenReturn("john@example.com");
        when(ownerService.findOwnerByUser(anyString())).thenReturn(OtherFixtures.someOwner());
        when(restaurantService.findRestaurantsByOwnerId(anyInt())).thenReturn(Collections.emptyList());
        //when
        String result = menuItemController.addMenuPage(model, session, principal);
        //then
        verify(model, times(1)).addAttribute(eq("restaurantsList"), any(List.class));
        verify(model, times(1)).addAttribute(eq("menuItemDTO"), any(MenuItemDTO.class));
        assertEquals(result,"add_menu");
    }

    @Test
    public void shouldAddMenuAndThrowException() {
        //given
        when(bindingResult.hasErrors()).thenReturn(true);
        when(restaurantService.findEmailFromString(anyString())).thenReturn("test@test.com");
        when(restaurantService.findRestaurantsByEmail(anyString())).thenThrow(new RuntimeException());
        //when, then
        assertThrows(ResponseStatusException.class, () -> {
            menuItemController.addMenu(new MenuItemDTO(), session, bindingResult, model);
        });
    }

}
