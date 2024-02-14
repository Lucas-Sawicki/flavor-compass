package org.example.api.dto.mapper;

import org.example.domain.Customer;
import org.example.domain.MenuItem;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

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
    @Named("mapCustomerToString")
    default String mapCustomerToString(Customer customer){
        return Optional.ofNullable(customer)
                .map(Customer::getName)
                .orElse(null);
    }
    @Named("mapOwnerToString")
    default String mapOwnerToString(Owner owner){
        return Optional.ofNullable(owner)
                .map(Owner::getName)
                .orElse(null);
    }
    @Named("mapRestaurantToString")
    default String mapCustomerToString(Restaurant restaurant){
        return Optional.ofNullable(restaurant)
                .map(Restaurant::getLocalName)
                .orElse(null);
    }
    @Named("mapMenuItemToString")
    default String mapMenuItemToString(MenuItem menuItem){
        return Optional.ofNullable(menuItem)
                .map(MenuItem::getName)
                .orElse(null);
    }
}
