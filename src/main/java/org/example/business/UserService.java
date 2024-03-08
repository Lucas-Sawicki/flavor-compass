package org.example.business;

import lombok.AllArgsConstructor;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findEntityByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userRepository.findEntityByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userRepository.existsByEmail(email);
    }

    public void createUser(User user) {
        if (user == null) {
            throw new CustomException("User cannot be null");
        }
        userRepository.saveUser(user);
    }
}
