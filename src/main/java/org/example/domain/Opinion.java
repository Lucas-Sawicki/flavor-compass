package org.example.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"opinionId", "stars", "comment", "customer", "opinionDate", "menuItem", "restaurant"})
@EqualsAndHashCode(of = "opinionId")
public class Opinion {

    Long opinionId;
    String stars;
    String comment;
    OffsetDateTime opinionDate;
    Customer customer;
    MenuItem menuItem;
    Restaurant restaurant;
}
