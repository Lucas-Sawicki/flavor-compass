package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "orderId")
@NoArgsConstructor
@ToString(of = {"orderId", "orderDate", "status", "deliveryTime"})
@AllArgsConstructor
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @Column(name = "order_number", unique = true)
    private Long orderNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "delivery_time")
    private LocalTime deliveryTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItemEntity> orderItems;
}
