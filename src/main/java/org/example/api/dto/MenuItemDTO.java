package org.example.api.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.management.PasswordMatches;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class MenuItemDTO {

    private String id;
    private String name;
    private String category;
    private String description;
    private String price;
    private String photoUrl;
    private String restaurantsList;
}
