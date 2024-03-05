package org.example.infrastructure.database.repository.mapper;

import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    @Mapping(target = "customer.user", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.orders", ignore = true)
    @Mapping(target = "owner.user", ignore = true)
    @Mapping(target = "owner.restaurants", ignore = true)
    User mapFromEntity(UserEntity user);

    UserEntity mapToEntity(User user);



}
