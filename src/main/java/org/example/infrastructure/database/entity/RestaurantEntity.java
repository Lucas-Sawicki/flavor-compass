package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
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
    private Long restaurantId;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "website")
    private String website;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<OpinionEntity> opinion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_hours_id")
    private OpeningHoursEntity openingHours;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<OrdersEntity> orders;


}
