package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Restaurant;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "owner", ignore = true)
    User mapFromEntity(UserEntity user);

    UserEntity mapToEntity(User user);

}
