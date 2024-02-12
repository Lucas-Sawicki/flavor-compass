package org.example.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "ownerId")
@NoArgsConstructor
@ToString(of = {"ownerId", "name", "surname", "email"})
@AllArgsConstructor
@Table(name = "owner")
public class OwnerEntity {

    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<RestaurantEntity> restaurant;

}
