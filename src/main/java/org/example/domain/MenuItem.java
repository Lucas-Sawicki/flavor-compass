package org.example.domain;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.example.infrastructure.database.entity.RestaurantEntity;

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

     Integer menuItemId;
     String name;
     String category;
     String description;
     @DecimalMin(value = "0.0", inclusive = false)
     BigDecimal price;
     String photoUrl;
     OrderItem orderItem;
     Restaurant restaurant;
     Set<Opinion> opinions;
     Set<Orders> orders;

     public Set<Opinion> getOpinions() {
          return opinions == null ? new HashSet<>() : opinions;
     }

     public Set<Orders> getOrders() {
          return orders == null ? new HashSet<>() : orders;
     }
}

