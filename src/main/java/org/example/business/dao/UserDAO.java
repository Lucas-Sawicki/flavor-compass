package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.Role;
import org.example.domain.User;

import java.util.Optional;

public interface UserDAO {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
