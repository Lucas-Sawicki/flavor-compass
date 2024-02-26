package org.example.business;

import lombok.AllArgsConstructor;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void createUser(User user) {
        userRepository.saveUser(user);
    }
}
