package org.example.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId", "name", "surname", "email"})
public class Customer {

     Long customerId;
     String name;
     String surname;
     String email;
     String phone;
     String password;
     Address address;
     Set<Opinion> opinions;
     Set<Orders> orders;

     public Set<Orders> getOrders() {
          return Objects.isNull(orders) ? new HashSet<>() : orders;
     }

     public Set<Opinion> getOpinions() {
          return  Objects.isNull(opinions) ? new HashSet<>() : opinions;
     }
}
