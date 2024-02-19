package org.example.domain;

import lombok.*;

import java.util.Collection;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"userId", "email", "active"})
public class User {

    Long userId;
    String email;
    String password;
    Boolean active;
    Customer customer;
    Owner owner;
    Collection<Role> roles;

}
