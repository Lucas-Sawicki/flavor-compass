package org.example.infrastructure.database.repository.mapper;
import org.example.domain.OpeningHours;
import org.example.domain.OrderItem;
import org.example.domain.Orders;
import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    @Mapping(target = "address.restaurant", ignore = true)
    @Mapping(target = "address.customer", ignore = true)
    @Mapping(target = "owner.user", ignore = true)
    @Mapping(target = "owner.restaurants", ignore = true)
    @Mapping(source = "orders", target = "orders", qualifiedByName = "mapFromOrders")
    @Mapping(source = "openingHours", target = "openingHours", qualifiedByName = "mapOpeningHours")
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


    @Named("mapFromOrders")
    default Set<Orders> mapFromOrders(Set<OrdersEntity> ordersEntities) {
        return ordersEntities.stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Orders mapFromEntity(OrdersEntity entity);
}
