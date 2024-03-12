package org.example.api.dto.mapper;

import org.example.api.dto.OpeningHoursDTO;
import org.example.domain.OpeningHours;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpeningHoursMapper extends LocalTimeMapper{
    default OpeningHours mapFromDto(OpeningHoursDTO openingHoursDTO) {
        return OpeningHours.builder()
                .dayOfWeek(openingHoursDTO.getDay())
                .openTime(mapLocalTimeFromString(openingHoursDTO.getOpenTime()))
                .closeTime(mapLocalTimeFromString(openingHoursDTO.getCloseTime()))
                .deliveryStartTime(mapLocalTimeFromString(openingHoursDTO.getDeliveryStartTime()))
                .deliveryEndTime(mapLocalTimeFromString(openingHoursDTO.getDeliveryEndTime()))
                .build();
    }
}
