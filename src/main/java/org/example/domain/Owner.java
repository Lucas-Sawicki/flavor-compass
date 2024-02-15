package org.example.domain;

import lombok.*;
import org.example.infrastructure.database.entity.UserEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "ownerId")
@ToString(of = {"ownerId", "name", "surname", "phone"})
public class Owner {

     Long ownerId;
     String name;
     String surname;
     String phone;
     Address address;
     User user;
     Set<Restaurant> restaurant;


     public Set<Restaurant> getRestaurants() {
          return Objects.isNull(restaurant) ? new HashSet<>() : restaurant;
     }


}
