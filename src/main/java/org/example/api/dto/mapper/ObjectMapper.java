package org.example.api.dto.mapper;

import org.example.domain.Customer;
import org.example.domain.MenuItem;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Named("mapOffsetDateTimeToString")
    default String mapOffsetDateTimeToString(OffsetDateTime offsetDateTime){
        return Optional.ofNullable(offsetDateTime)
                .map(odt -> offsetDateTime.atZoneSameInstant(ZoneOffset.UTC))
                .map(odt -> odt.format(DATE_FORMAT))
                .orElse(null);
    }
    @Named("mapDeliveryTimeToString")
    default String mapCustomerToString(LocalTime time){
        return Optional.ofNullable(time)
                .map(String::valueOf)
                .orElse(null);
    }
    @Named("mapCustomer")
    default Integer mapCustomer(Customer customer){
        return Optional.ofNullable(customer)
                .map(Customer::getCustomerId)
                .orElse(null);
    }
    @Named("mapRestaurant")
    default Integer mapCustomer(Restaurant restaurant){
        return Optional.ofNullable(restaurant)
                .map(Restaurant::getRestaurantId)
                .orElse(null);
    }

}
