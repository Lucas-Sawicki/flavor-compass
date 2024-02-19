package org.example.domain;

import lombok.*;

import java.util.Collection;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "roleId")
@ToString
public class Role {


    int roleId;
    String role;
    Collection<User> users;
}
