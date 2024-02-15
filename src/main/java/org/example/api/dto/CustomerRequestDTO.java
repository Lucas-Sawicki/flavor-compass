package org.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private String customerName;
    private String customerSurname;
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    private String customerEmail;
    private String customerPassword;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressStreet;
    private String customerAddressPostalCode;



}
