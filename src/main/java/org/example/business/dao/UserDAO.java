package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;

import java.util.Optional;

public interface UserDAO {
    User saveUser(User user);
    Optional<UserEntity> findByEmail(String email);
    Optional<User>  findOwnerById(Long id);
    Boolean existsByEmail(String email);

}
