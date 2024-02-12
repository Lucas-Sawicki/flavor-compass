package org.example.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"ownerId", "name", "surname", "email"})
public class Owner {

     Long ownerId;
     String name;
     String surname;
     String phone;
     String email;
     Address address;
     String password;
     Set<Restaurant> restaurant;


     public Set<Restaurant> getRestaurants() {
          return Objects.isNull(restaurant) ? new HashSet<>() : restaurant;
     }


}
