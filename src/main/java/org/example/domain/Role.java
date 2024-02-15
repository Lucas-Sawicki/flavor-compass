package org.example.domain;

import lombok.*;

@Value
@With
@Builder
@EqualsAndHashCode(of = "roleId")
@ToString
public class Role implements Comparable<Role>{


    int roleId;
    String role;

    @Override
    public int compareTo(Role o) {
        return this.getRole().compareTo(o.getRole());
    }
}
