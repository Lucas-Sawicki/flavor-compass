package org.example.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "menuItemId")
@ToString(of = {"menuItemId", "name", "category", "price"})
public class MenuItem {

     Long menuItemId;
     String name;
     String category;
     String description;
     BigDecimal price;
     String photoUrl;
     Boolean available;
     OrderItem orderItem;
     Set<Opinion> opinions;
     Set<Orders> orders;



     public Set<Opinion> getOpinions() {
          return Objects.isNull(opinions) ? new HashSet<>() : opinions;
     }
     public Set<Orders> getOrders() {
          return Objects.isNull(orders) ? new HashSet<>() : orders;
     }

}
