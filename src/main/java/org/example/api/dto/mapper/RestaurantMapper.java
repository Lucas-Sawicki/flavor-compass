package org.example.api.dto.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.AddressDTO;
import org.example.api.dto.OpeningHoursDTO;
import org.example.api.dto.RestaurantDTO;
import org.example.domain.Address;
import org.example.domain.OpeningHours;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Mapper(componentModel = "spring")
public interface RestaurantMapper extends LocalTimeMapper {

    default Restaurant map(RestaurantDTO restaurantDTO, AddressDTO addressDTO, Owner owner) {
        Restaurant restaurant = Restaurant.builder()
                .localName(restaurantDTO.getRestaurantName())
                .website(restaurantDTO.getRestaurantWebsite())
                .phone(restaurantDTO.getRestaurantPhone())
                .email(restaurantDTO.getRestaurantEmail())
                .address(Address.builder()
                        .country(addressDTO.getAddressCountry())
                        .city(addressDTO.getAddressCity())
                        .postalCode(addressDTO.getAddressPostalCode())
                        .street(addressDTO.getAddressStreet())
                        .build())
                .owner(Owner.builder()
                        .name(owner.getName())
                        .surname(owner.getSurname())
                        .phone(owner.getPhone())
                        .ownerId(owner.getOwnerId())
                        .build())
                .build();


        Map<DayOfWeek, OpeningHours> openingHoursMap = new TreeMap<>();
        for (Map.Entry<DayOfWeek, OpeningHoursDTO> entry : restaurantDTO.getOpeningHours().entrySet()) {
            DayOfWeek key = entry.getKey();
            OpeningHoursDTO dto = entry.getValue();
            OpeningHours openingHours = OpeningHours.builder()
                    .dayOfWeek(key)
                    .openTime(mapLocalTimeFromString(dto.getOpenTime()))
                    .closeTime(mapLocalTimeFromString(dto.getCloseTime()))
                    .deliveryStartTime(mapLocalTimeFromString(dto.getDeliveryStartTime()))
                    .deliveryEndTime(mapLocalTimeFromString(dto.getDeliveryEndTime()))
                    .build();

            openingHoursMap.put(entry.getKey(), openingHours);
        }
        return restaurant.withOpeningHours(openingHoursMap);
    }

}
