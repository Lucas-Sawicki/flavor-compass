package org.example.domain;

import lombok.*;

@Value
@With
@Builder
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "menuItem", "quantity", "order"})
public class OrderItem {

    Long orderItemId;
    Integer quantity;
    MenuItem menuItem;
    Orders order;
}
