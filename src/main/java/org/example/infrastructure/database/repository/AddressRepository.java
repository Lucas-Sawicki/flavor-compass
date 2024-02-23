package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.business.dao.AddressDAO;
import org.example.domain.Address;
import org.example.infrastructure.database.entity.AddressEntity;
import org.example.infrastructure.database.repository.jpa.AddressJpaRepository;
import org.example.infrastructure.database.repository.mapper.AddressEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressEntityMapper;
    @Override
    public Address saveAddress(Address address) {
        AddressEntity addressEntity = addressEntityMapper.mapToEntity(address);
        AddressEntity saved = addressJpaRepository.saveAndFlush(addressEntity);
        return addressEntityMapper.mapFromEntity(saved);
    }
}
