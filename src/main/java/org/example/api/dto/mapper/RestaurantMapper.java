package org.example.api.dto.mapper;

import org.example.api.dto.CustomerRequestDTO;
import org.example.api.dto.OrdersDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.domain.Address;
import org.example.domain.Customer;
import org.example.domain.Orders;
import org.example.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
//    @Mapping(source = "restaurantAddressCountry", target = "menuItem", qualifiedByName = "mapMenuItemToString")
//    RestaurantDTO map (Restaurant restaurant);


    default Restaurant map(RestaurantDTO dto) {
        return Restaurant.builder()
                .localName(dto.getRestaurantName())
                .website(dto.getRestaurantWebsite())
                .phone(dto.getRestaurantPhone())
                .address(Address.builder()
                        .country(dto.getRestaurantAddressCountry())
                        .city(dto.getRestaurantAddressCity())
                        .postalCode(dto.getRestaurantAddressPostalCode())
                        .street(dto.getRestaurantAddressStreet())
                        .build())
                .build();
    }
}
