package org.example.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@With
@Builder
@EqualsAndHashCode(of = "orderNumber")
@ToString(of = {"orderId", "orderDate", "status", "deliveryTime"})
public class Orders {

    Integer orderId;
    OffsetDateTime orderDate;
    Long orderNumber;
    String status;
    LocalTime deliveryTime;
    Customer customer;
    Restaurant restaurant;
    List<OrderItem> orderItems;

    public String getStatus() {
        return status == null ? withStatus("pending").status : this.status;
    }

    public BigDecimal getTotalCost() {
        return orderItems.stream()
                .map(orderItem -> orderItem.getMenuItem().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}