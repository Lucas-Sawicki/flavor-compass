package org.example.domain;

import lombok.*;
import org.example.infrastructure.database.entity.UserEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "customerId")
@ToString(of = {"customerId", "name", "surname", "phone"})
public class Customer {

    Long customerId;
    String name;
    String surname;
    String phone;
    Address address;
    User user;
    Set<Opinion> opinions;
    Set<Orders> orders;

    public Set<Orders> getOrders() {
        return Objects.isNull(orders) ? new HashSet<>() : orders;
    }

    public Set<Opinion> getOpinions() {
        return Objects.isNull(opinions) ? new HashSet<>() : opinions;
    }
}
