package org.example.api.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.MenuItemDTO;
import org.example.api.dto.OpeningHoursDTO;

import java.time.DayOfWeek;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestRestaurantUpdateDTO {
    private Map<DayOfWeek, OpeningHoursDTO> openingHoursDTO;

    private MenuItemDTO menuItemDTO;
    private String restaurantEmail;
    private AddressDTO addressDTO;
}
