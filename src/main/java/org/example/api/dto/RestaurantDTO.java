package org.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.management.ValidEmail;
import org.example.domain.Owner;

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
    private String restaurantAddressCountry;
    private String restaurantAddressCity;
    private String restaurantAddressStreet;
    private String restaurantAddressPostalCode;

}
