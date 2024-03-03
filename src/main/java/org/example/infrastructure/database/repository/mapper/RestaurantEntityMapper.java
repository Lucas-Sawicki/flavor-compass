package org.example.infrastructure.database.repository.mapper;
import org.example.domain.OpeningHours;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(target = "address.customer", ignore = true)
    @Mapping(target = "owner.user", ignore = true)
    @Mapping(target = "owner.restaurants", ignore = true)
    @Mapping(target = "openingHours", qualifiedByName = "mapOpeningHours")
    Restaurant mapFromEntity(RestaurantEntity restaurant);

    RestaurantEntity mapToEntity(Restaurant restaurant);


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
