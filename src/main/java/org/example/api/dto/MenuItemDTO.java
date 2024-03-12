package org.example.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.api.management.PasswordMatches;
import org.example.domain.Category;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = {"name","category","description","price"})
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {

    private String id;
    @NotBlank(message = "Field of name can't be blank")
    private String name;
    private Category category;
    @NotBlank(message = "Field of description can't be blank")
    private String description;
    @Digits(integer = 5,
            fraction = 2,
            message = "Invalid price format. Maximum of 5 digits before the decimal point and 2 digits after the decimal point.")
    private String price;
    @URL(message = "website need to start with: http://")
    private String photoUrl;
    private String restaurantsList;
}
