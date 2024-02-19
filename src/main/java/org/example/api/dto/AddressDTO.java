package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String addressCountry;
    private String addressCity;
    private String addressStreet;
    private String addressPostalCode;

    private boolean customer;


}
