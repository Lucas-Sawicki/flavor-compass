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
public class LoginDTO {

    @Email
    @NotBlank(message = "Email is required")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "The password should be at least 8 characters long")
    private String password;

}
