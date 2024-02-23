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
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapWithoutUser")
    User mapFromEntity(UserEntity user);
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "customer", ignore = true)
    UserEntity mapToEntity(User user);


    @Named("mapWithoutUser")
    default Set<Role> mapWithoutUser(Set<RoleEntity> entities) {
        return entities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "users", ignore = true)
    Role mapFromEntity(RoleEntity entity);
}
