package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Restaurant;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {

    @Query("""
    SELECT res FROM RestaurantEntity res
    WHERE res.owner = :owner
    """)
    List<RestaurantEntity> findRestaurantsByOwner(@Param("owner") OwnerEntity owner);
    Boolean existsByEmail(String email);

    RestaurantEntity findByEmail(String email);

}
