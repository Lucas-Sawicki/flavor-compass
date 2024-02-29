package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.UserDAO;
import org.example.domain.User;
import org.example.infrastructure.database.entity.UserEntity;
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
    public User saveUser(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        UserEntity saved = userJpaRepository.save(userEntity);
        return userEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).get();
        User user = userEntityMapper.mapFromEntity(userEntity);
        return Optional.of(user);
    }
    @Override
    public Optional<UserEntity> findEntityByEmail(String email) {
       return userJpaRepository.findByEmail(email);

    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
