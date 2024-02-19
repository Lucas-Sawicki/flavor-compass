package org.example.domain;

import lombok.*;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@With
@EqualsAndHashCode(of = "orderNumber")
@Builder
@ToString(of = {"orderId", "orderDate", "orderNumber", "status", "customer", "restaurant"})

public class Orders {

    Long orderId;
    OffsetDateTime orderDate;
    Long orderNumber;
    String status;
    LocalTime deliveryTime;
    Customer customer;
    Restaurant restaurant;
    MenuItem menuItem;
    List<OrderItem> orderItems;

    public String getStatus() {
        if(status == null){
            return withStatus("pending").status;
        } else {
            return this.status;
        }
    }
}
