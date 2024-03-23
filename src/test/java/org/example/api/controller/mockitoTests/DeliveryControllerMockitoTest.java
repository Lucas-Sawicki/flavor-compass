package org.example.api.controller.mockitoTests;

import org.example.api.controller.DeliveryController;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.mapper.DeliveryMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.teryt.LoadDataService;
import org.example.domain.Owner;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerMockitoTest {

    @InjectMocks
    DeliveryController deliveryController;
    @Mock
    DeliveryMapper deliveryMapper;

    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    LoadDataService loadDataService;

    @Mock
    OwnerService ownerService;

    @Mock
    RestaurantService restaurantService;

    @Mock
    Principal principal;

    @Mock
    Model model;

    @Test
    public void shouldShowPage() {
        // given
        String voi = "someVoivodeship";
        String cou = "someCounty";
        String cit = "someCity";
        Owner owner = OtherFixtures.someOwner();
        String email = owner.getUser().getEmail();

        when(principal.getName()).thenReturn(email);
        when(ownerService.findOwnerByUser(email)).thenReturn(owner);
        when(loadDataService.loadWojPowFromCSV()).thenReturn(new HashMap<>());
        when(loadDataService.loadPowGmiFromCSV()).thenReturn(new HashMap<>());
        when(loadDataService.loadGmiSymFromCSV()).thenReturn(new HashMap<>());
        when(restaurantService.findRestaurantsByOwnerId(anyInt())).thenReturn(new ArrayList<>());

        // when
        String result = deliveryController.showPage(voi, cou, cit, model, principal);

        // then
        assertEquals("delivery_range", result);
        verify(model, times(1)).addAttribute(eq("cit"), eq(cit));
        verify(model, times(1)).addAttribute(eq("restaurantsList"), anyList());
        verify(model, times(1)).addAttribute(eq("voivodeships"), anyMap());
    }

    @Test
    public void whenAddStreetsThrowsExceptionThenReturnsInternalServerError() {
        // given
        String cit = "cit";
        String restaurant = "restaurant";
        List<String> approvedNames = new ArrayList<>();
        approvedNames.add("street1");
        approvedNames.add("street2");

        doThrow(new RuntimeException()).when(deliveryMapper).map(cit, restaurant, approvedNames);

        // when and Then
        ResponseStatusException exception = org.junit.jupiter.api.Assertions
                .assertThrows(ResponseStatusException.class, () -> {
                    deliveryController.addStreets(cit, restaurant, approvedNames, redirectAttributes);
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        verify(redirectAttributes, times(1))
                .addFlashAttribute("error", "Something gone wrong when saving delivery address");
    }
}
