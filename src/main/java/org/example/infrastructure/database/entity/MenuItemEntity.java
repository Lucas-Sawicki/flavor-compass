package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "menuItemId")
@NoArgsConstructor
@ToString(of = {"menuItemId", "name", "category", "price"})
@AllArgsConstructor
@Table(name = "menu_item")
public class MenuItemEntity {

    @Id
    @Column(name = "menu_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column (name = "available")
    private Boolean available;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "menuItem")
    private OrderItemEntity orderItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menuItem")
    private Set<OpinionEntity> opinions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menuItem")
    private Set<OrdersEntity> orders;

}
