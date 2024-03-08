package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotBlank(message = "Field can't be blank")
    private String addressCountry;
    @NotBlank(message = "Field can't be blank")
    private String addressCity;
    @NotBlank(message = "Field can't be blank")
    private String addressStreet;
    @NotBlank(message = "Field can't be blank")
    private String addressPostalCode;

    private boolean customer;


}
