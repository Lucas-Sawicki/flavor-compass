package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.example.infrastructure.database.repository.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    OwnerJpaRepository ownerJpaRepository;
    OwnerEntityMapper ownerEntityMapper;
    UserEntityMapper userEntityMapper;


    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.saveAndFlush(toSave);
        return ownerEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Owner findOwnerById(Integer id) {
        OwnerEntity ownerEntity = ownerJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner not found"));
        return ownerEntityMapper.mapFromEntity(ownerEntity);
    }

    @Override
    public Owner findOwnerByUser(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        OwnerEntity ownerEntity = ownerJpaRepository.findByUser(userEntity);
        return ownerEntityMapper.mapFromEntity(ownerEntity);
    }
}
