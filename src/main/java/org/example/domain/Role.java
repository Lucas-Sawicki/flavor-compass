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


    Integer roleId;
    String role;
    Set<User> users;
}
