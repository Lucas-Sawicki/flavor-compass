package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "opinionId")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "opinion")
public class OpinionEntity {

    @Id
    @Column(name = "opinion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opinionId;

    @Column(name = "stars")
    private String stars;

    @Column(name = "comment")
    private String comment;

    @Column(name = "opinion_date")
    private OffsetDateTime opinionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;



}
