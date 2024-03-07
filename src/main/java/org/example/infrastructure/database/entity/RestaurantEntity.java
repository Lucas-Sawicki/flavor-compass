package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "restaurantId")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "website")
    private String website;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<MenuItemEntity> menuItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<OpinionEntity> opinions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<OrdersEntity> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "restaurant_opening_hours",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "opening_hours_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, OpeningHoursEntity> openingHours;


    @ManyToMany(mappedBy = "restaurants")
    private List<DeliveryRangeEntity> deliveryRange;
}
