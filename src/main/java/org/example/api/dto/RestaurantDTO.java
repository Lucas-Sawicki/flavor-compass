package org.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.management.ValidEmail;
import org.example.domain.OpeningHours;
import org.example.domain.Owner;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {


    private String restaurantName;
    private String restaurantWebsite;
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String restaurantPhone;
    @ValidEmail
    private String restaurantEmail;

    private AddressDTO addressDTO;
    @Builder.Default
    private Map<DayOfWeek, OpeningHoursDTO> openingHours = new TreeMap<>();
}
