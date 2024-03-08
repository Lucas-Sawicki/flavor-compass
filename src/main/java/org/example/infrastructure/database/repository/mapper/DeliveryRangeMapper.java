package org.example.infrastructure.database.repository.mapper;

import org.example.domain.DeliveryRange;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryRangeMapper {
    @Mapping(source = "restaurants", target = "restaurants", qualifiedByName = "mapRestaurant")
    DeliveryRange mapFromEntity(DeliveryRangeEntity entity);

    DeliveryRangeEntity mapToEntity(DeliveryRange deliveryRange);


    @Named("mapRestaurant")
    default List<Restaurant> mapRestaurant(List<RestaurantEntity> restaurantEntities) {
        return restaurantEntities.stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(target = "address.customer", ignore = true)
    @Mapping(target = "owner.user", ignore = true)
    @Mapping(target = "owner.restaurants", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "opinions", ignore = true)
    @Mapping(source = "openingHours", target = "openingHours", qualifiedByName = "mapOpeningHours")
    Restaurant mapFromEntity(RestaurantEntity entity);

    @Named("mapOpeningHours")
    default Map<DayOfWeek, OpeningHours> mapOpeningHours(Map<DayOfWeek, OpeningHoursEntity> dayOfWeekOpeningHoursMap) {
        return dayOfWeekOpeningHoursMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapFromEntity(entry.getValue()),
                        (oldValue, newValue) -> oldValue,
                        TreeMap::new
                ));
    }
    @Mapping(target = "restaurants", ignore = true)
    OpeningHours mapFromEntity(OpeningHoursEntity entity);
}
