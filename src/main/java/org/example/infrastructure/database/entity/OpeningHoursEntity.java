package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "openingHoursId")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "opening_hours")
public class OpeningHoursEntity {
    @Id
    @Column(name = "opening_hours_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long openingHoursId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_the_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "delivery_start_time")
    private LocalTime deliveryStartTime;

    @Column(name = "delivery_end_time")
    private LocalTime deliveryEndTime;

    @ManyToMany(mappedBy = "openingHours")
    private Set<RestaurantEntity> restaurants;
}
