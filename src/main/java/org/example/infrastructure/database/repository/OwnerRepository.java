package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    OwnerJpaRepository ownerJpaRepository;
    OwnerEntityMapper ownerEntityMapper;

    @Override
    public Optional<Owner> findByEmail(String email) {
        return  ownerJpaRepository.findByEmail(email)
                .map(ownerEntityMapper::mapFromEntity);
    }

    @Override
    public void saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.saveAndFlush(toSave);
        ownerEntityMapper.mapFromEntity(saved);
    }
}
