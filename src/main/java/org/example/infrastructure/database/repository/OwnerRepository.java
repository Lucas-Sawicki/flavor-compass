package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.domain.Owner;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    OwnerJpaRepository ownerJpaRepository;
    OwnerEntityMapper ownerEntityMapper;


    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.saveAndFlush(toSave);
        return ownerEntityMapper.mapFromEntity(saved);
    }
}
