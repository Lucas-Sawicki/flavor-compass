package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.UserDAO;
import org.example.domain.User;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return  userJpaRepository.findByEmail(email)
                .map(userEntityMapper::mapFromEntity);
    }
}
