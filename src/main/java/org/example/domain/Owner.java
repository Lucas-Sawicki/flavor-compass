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

     Integer ownerId;
     String name;
     String surname;
     String phone;
     User user;
     Set<Restaurant> restaurants;


     public Set<Restaurant> getRestaurants() {
          return Objects.isNull(restaurants) ? new HashSet<>() : restaurants;
     }


}
