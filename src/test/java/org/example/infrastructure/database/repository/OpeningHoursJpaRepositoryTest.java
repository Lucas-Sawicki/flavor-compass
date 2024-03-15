package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.OpeningHoursEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.OpeningHoursJpaRepository;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OpeningHoursJpaRepositoryTest {

    private OpeningHoursJpaRepository openingHoursJpaRepository;

    @Test
    @Sql("/test-data.sql")
    public void shouldSaveOpeningHoursAndFindByRestaurantsSuccessfully() {
        // given
        RestaurantEntity restaurantEntity = EntityFixtures.someRestaurant1();
        Map<DayOfWeek, OpeningHoursEntity> openingHoursMap = EntityFixtures.someOpeningHours();

        // when
        List<OpeningHoursEntity> found = openingHoursJpaRepository.findByRestaurants(restaurantEntity);

        // then
        assertThat(found).hasSize(7)
                .extracting(OpeningHoursEntity::getOpenTime)
                .containsOnly(
                        openingHoursMap.get(DayOfWeek.MONDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.TUESDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.WEDNESDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.THURSDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.FRIDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.SATURDAY).getOpenTime(),
                        openingHoursMap.get(DayOfWeek.SUNDAY).getOpenTime());
    }

}
