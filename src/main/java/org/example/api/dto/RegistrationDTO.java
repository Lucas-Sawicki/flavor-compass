package org.example.api.dto;

import jakarta.validation.constraints.*;
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
public class RegistrationDTO {


    @NotBlank(message = "Field name can't be blank")
    private String name;

    @NotBlank(message = "Field surname can't be blank")
    private String surname;

    @NotBlank
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$", message = "Please enter phone in valid pattern, example: +00 000 000 000")
    private String phone;

    private boolean restApiUser;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "The password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "The password should be at least 8 characters long")
    private String matchingPassword;

    private AddressDTO addressDTO;
}
