package org.example.infrastructure.database.repository;

import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantJpaRepositoryTest {

    private RestaurantJpaRepository restaurantJpaRepository;

    @Test
    @Sql("/test-data.sql")
    public void findRestaurantsByOwner_ReturnsCorrectRestaurants() {
        // given
        OwnerEntity owner = EntityFixtures.someOwner1();
        RestaurantEntity restaurant1 = EntityFixtures.someRestaurant1();

        // when
        List<RestaurantEntity> restaurants = restaurantJpaRepository.findRestaurantsByOwner(owner);

        // then
        assertThat(restaurants).hasSize(1);
        assertThat(restaurants).containsExactlyInAnyOrder(restaurant1);
    }

    @Test
    @Sql("/test-data.sql")
    public void existsByEmail_ReturnsTrueIfExists() {
        // given
        String email = "someEmail@com.pl";
        RestaurantEntity restaurant = EntityFixtures.someRestaurant2();

        // when
        boolean exists = restaurantJpaRepository.existsByEmail(restaurant.getEmail());
        boolean notExists = restaurantJpaRepository.existsByEmail(email);

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @Sql("/test-data.sql")
    public void findByEmail_ReturnsCorrectRestaurant() {
        // given
        String email = "test@example.com";
        RestaurantEntity restaurant = EntityFixtures.someRestaurant1();
        // when
        RestaurantEntity shouldNotFind = restaurantJpaRepository.findByEmail(email);
        RestaurantEntity foundRestaurant = restaurantJpaRepository.findByEmail(restaurant.getEmail());

        // then
        assertThat(shouldNotFind).isNull();
        assertThat(foundRestaurant).isNotNull();
    }

}
