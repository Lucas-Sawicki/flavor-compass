package org.example.domain;

import lombok.*;
import org.example.infrastructure.database.entity.RestaurantEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "openingHoursId")
@ToString
public class OpeningHours {

    Long openingHoursId;
    DayOfWeek dayOfWeek;
    LocalTime openTime;
    LocalTime closeTime;
    LocalTime deliveryStartTime;
    LocalTime deliveryEndTime;
    Set<Restaurant> restaurants;
}
