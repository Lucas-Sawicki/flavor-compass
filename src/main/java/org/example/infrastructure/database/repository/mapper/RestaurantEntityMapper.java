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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(source = "opinions", target = "opinions", qualifiedByName = "mapOpinions")
    @Mapping(target = "openingHours", ignore = true)
    @Mapping(source = "orders", target = "orders", qualifiedByName = "mapOrders")
    Restaurant mapFromEntity(RestaurantEntity restaurant);
    @Mapping(target = "openingHours", source = "openingHours", qualifiedByName = "mapOpeningHours")
    @Mapping(target = "owner.restaurants", ignore = true)
    RestaurantEntity mapToEntity(Restaurant restaurant);

    @Named("mapOpinions")
    default Set<Opinion> mapOpinions(Set<OpinionEntity> opinionsEntities) {
        return opinionsEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "restaurant", ignore = true)
    Opinion mapFromEntity(OpinionEntity entity);
    @Named("mapOrders")
    default Set<Orders> mapOrders(Set<OrdersEntity> opinionsEntities) {
        return opinionsEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "restaurant", ignore = true)
    Orders mapFromEntity(OrdersEntity entity);

    @Named("mapOpeningHours")
    default Map<DayOfWeek, OpeningHoursEntity> mapOpeningHours(Map<DayOfWeek, OpeningHours> openingHoursEntities) {
        return openingHoursEntities.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapToEntity(entry.getValue())
                ));
    }


    OpeningHoursEntity mapToEntity(OpeningHours entity);
}
