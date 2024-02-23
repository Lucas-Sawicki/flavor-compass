package org.example.domain;

import lombok.*;
import org.example.infrastructure.database.entity.OpeningHoursEntity;

import java.time.DayOfWeek;
import java.util.*;

@Value
@With
@Builder
@EqualsAndHashCode(of = "phone")
@ToString(of = {"restaurantId", "localName", "website", "phone", "owner", "email"})
public class Restaurant {

     Long restaurantId;
     String localName;
     String website;
     String phone;
     String email;
     Address address;
     Owner owner;
     Map<DayOfWeek, OpeningHours> openingHours;

     Set<Opinion> opinions;
     Set<Orders> orders;

     public Set<Orders> getOrders() {
          return Objects.isNull(orders) ? new HashSet<>() : orders;
     }

     public Set<Opinion> getOpinions() {
          return  Objects.isNull(opinions) ? new HashSet<>() : opinions;
     }
     public Map<DayOfWeek, Long> getOpeningHoursId(Map<DayOfWeek, OpeningHours> openingHours) {
          Map<DayOfWeek, Long> openingHoursIdMap = new TreeMap<>();
          for (Map.Entry<DayOfWeek, OpeningHours> entry : openingHours.entrySet()) {
               openingHoursIdMap.put(entry.getKey(), entry.getValue().getOpeningHoursId());
          }
          return openingHoursIdMap;
     }
     public Restaurant withOpeningHoursId(Map<DayOfWeek, Long> openingHoursIdMap) {
          Map<DayOfWeek, OpeningHours> newOpeningHours = new HashMap<>();
          Long firstOpeningHoursId = openingHoursIdMap.values().stream().findFirst().orElse(null);
          for (Map.Entry<DayOfWeek, Long> entry : openingHoursIdMap.entrySet()) {
               OpeningHours openingHours = OpeningHours.builder()
                       .openingHoursId(firstOpeningHoursId)
                       .build();
               newOpeningHours.put(entry.getKey(), openingHours);
          }
          return Restaurant.builder()
                  .restaurantId(this.restaurantId)
                  .localName(this.localName)
                  .website(this.website)
                  .phone(this.phone)
                  .email(this.email)
                  .address(this.address)
                  .owner(this.owner)
                  .openingHours(newOpeningHours)
                  .opinions(this.opinions)
                  .orders(this.orders)
                  .build();
     }

}
