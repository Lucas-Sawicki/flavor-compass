package org.example.api.dto.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestOwnerDTO {

    @NotBlank(message = "Field name can't be blank")
    private String name;

    @NotBlank(message = "Field surname can't be blank")
    private String surname;

    @NotBlank
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$", message = "Please enter phone in valid pattern, example: +00 000 000 000")
    private String phone;

}
