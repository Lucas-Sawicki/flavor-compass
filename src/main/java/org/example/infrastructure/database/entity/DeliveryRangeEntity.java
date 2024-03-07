package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "deliveryRangeId")
@NoArgsConstructor
@ToString(of = {"deliveryRangeId", "city", "street", "restaurants"})
@AllArgsConstructor
@Table(name = "delivery_range")
public class DeliveryRangeEntity {

    @Id
    @Column(name = "delivery_range_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryRangeId;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "delivery",
            joinColumns = @JoinColumn(name = "delivery_range_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<RestaurantEntity> restaurants;

}
