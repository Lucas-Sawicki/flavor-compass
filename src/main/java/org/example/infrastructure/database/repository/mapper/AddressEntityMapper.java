package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Address;
import org.example.infrastructure.database.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Address mapFromEntity(AddressEntity addressEntity);

    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    AddressEntity mapToEntity(Address address);
}
