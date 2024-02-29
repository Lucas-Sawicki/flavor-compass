package org.example.domain;

import lombok.*;

@Value
@With
@Builder
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "menuItem", "quantity", "order"})
public class OrderItem {

    Integer orderItemId;
    Integer quantity;
    MenuItem menuItem;
    Orders order;
}
