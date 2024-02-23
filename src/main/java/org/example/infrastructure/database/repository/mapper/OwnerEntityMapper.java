package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.domain.User;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface OwnerEntityMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "restaurants", ignore = true)
    Owner mapFromEntity(OwnerEntity customer);

    @Mapping(target = "restaurants", ignore = true)
    @Mapping(target = "user", ignore = true)
    OwnerEntity mapToEntity(Owner customer);
}
