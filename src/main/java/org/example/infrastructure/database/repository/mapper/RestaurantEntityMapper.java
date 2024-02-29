package org.example.infrastructure.database.repository.mapper;

import org.example.domain.OpeningHours;
import org.example.domain.Opinion;
import org.example.domain.Orders;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(target = "address.customer", ignore = true)
    @Mapping(target = "owner.user", ignore = true)
    @Mapping(target = "owner.restaurants", ignore = true)
    @Mapping(target = "openingHours", ignore = true)

    Restaurant mapFromEntity(RestaurantEntity restaurant);

    RestaurantEntity mapToEntity(Restaurant restaurant);


}
