package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Owner;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface OwnerEntityMapper {

    @Mapping(target = "restaurants", ignore = true)
    Owner mapFromEntity(OwnerEntity customer);

    @Mapping(target = "restaurants", ignore = true)
    OwnerEntity mapToEntity(Owner customer);

}
