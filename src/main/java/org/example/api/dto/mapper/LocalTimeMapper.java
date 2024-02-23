package org.example.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Mapper(componentModel = "spring")
public interface LocalTimeMapper {

    default LocalTime mapLocalTimeFromString(String timeString) {
        if (timeString == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeString, formatter);
    }
}
