package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.rest.RestOwnerDTO;
import org.example.business.RoleService;
import org.example.business.dao.UserDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;
    private final UserEntityMapper userEntityMapper;


    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        UserEntity saved = userJpaRepository.save(userEntity);
        userEntityMapper.mapFromEntity(saved);
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

    @Override
    public void setAsOwner(User user, RestOwnerDTO body) {
        Owner owner = Owner.builder()
                .name(body.getName())
                .surname(body.getSurname())
                .phone(body.getPhone())
                .build();
        OwnerEntity ownerEntity = ownerEntityMapper.mapToEntity(owner);
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        ownerEntity.setUser(userEntity);
        userEntity.setOwner(ownerEntity);
        userJpaRepository.saveAndFlush(userEntity);
    }

    @Override
    public void deleteUser(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        userJpaRepository.delete(userEntity);
    }
}
