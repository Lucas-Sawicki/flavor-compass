package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Restaurant;
import org.example.domain.User;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    User mapFromEntity(UserEntity user);

    UserEntity mapToEntity(User user);

}
