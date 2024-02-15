package org.example.domain;

import lombok.*;
import org.example.business.dao.OpeningHoursDAO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
     OpeningHours openingHours;

     Set<Opinion> opinions;
     Set<Orders> orders;

     public Set<Orders> getOrders() {
          return Objects.isNull(orders) ? new HashSet<>() : orders;
     }

     public Set<Opinion> getOpinions() {
          return  Objects.isNull(opinions) ? new HashSet<>() : opinions;
     }
}
