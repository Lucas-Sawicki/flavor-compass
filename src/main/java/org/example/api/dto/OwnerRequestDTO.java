package org.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.infrastructure.database.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequestDTO {

    private String ownerName;
    private String ownerSurname;
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String ownerPhone;
    private String ownerEmail;
    private String ownerPassword;



}
