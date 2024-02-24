package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseDTO {

    private UserEntity user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }
}
