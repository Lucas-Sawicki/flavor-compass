package org.example.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api.management.PasswordMatches;
import org.example.api.management.ValidEmail;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class RegistrationDTO {


    private String name;
    private String surname;
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private boolean restApiUser;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;


    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;


    private AddressDTO addressDTO;
}
