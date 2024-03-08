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
@PasswordMatches
public class LoginDTO {

    @Email
    @NotNull
    @NotEmpty
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Password is required")
    private String password;

}
