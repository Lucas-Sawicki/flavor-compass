package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.management.PasswordMatches;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class DeliveryRangeDTO {

    String restaurant;
    @NotBlank
    String city;
    @NotBlank
    String street;
    List<String> streets;
}
