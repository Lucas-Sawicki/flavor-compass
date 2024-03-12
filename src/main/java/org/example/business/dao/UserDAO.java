package org.example.business.dao;

import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.rest.RestOwnerDTO;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;

import java.util.Optional;

public interface UserDAO {
    void saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<UserEntity> findEntityByEmail(String email);
    Boolean existsByEmail(String email);

    void setAsOwner(User user, RestOwnerDTO body);

    void deleteUser(User user);
}
