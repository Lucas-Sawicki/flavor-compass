package org.example.domain;

import lombok.*;

import java.time.LocalTime;

@Value
@With
@Builder
@EqualsAndHashCode(of = "openingHoursId")
@ToString
public class OpeningHours {

    Long openingHoursId;
    String dayOfTheWeek;
    LocalTime openTime;
    LocalTime closeTime;
    LocalTime deliveryStartTime;
    LocalTime deliveryEndTime;
    Restaurant restaurant;
}
