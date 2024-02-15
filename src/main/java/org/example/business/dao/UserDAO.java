package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findByEmail(String email);

}
