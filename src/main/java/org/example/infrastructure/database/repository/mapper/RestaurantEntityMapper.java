package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface RestaurantEntityMapper {

    @Mapping(target = "restaurantOwner.restaurants", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity restaurant);

    @Mapping(target = "restaurantOwner.restaurants", ignore = true)
    RestaurantEntity mapToEntity(Restaurant restaurant);

}
