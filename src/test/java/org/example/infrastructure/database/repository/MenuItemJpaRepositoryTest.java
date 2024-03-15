package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.MenuItemEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RestaurantEntity;
import org.example.infrastructure.database.repository.jpa.MenuItemJpaRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.util.EntityFixtures.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MenuItemJpaRepositoryTest {

    private MenuItemJpaRepository menuItemJpaRepository;


    @Test
    @Sql("/test-data.sql")
    public void shouldFindAllMenuItemsForRestaurantSuccessfully() {
        // given
        RestaurantEntity restaurant = someRestaurant1();

        MenuItemEntity menuItem1 = someMenuItem1();
        MenuItemEntity menuItem2 = someMenuItem2();
        var menuItems = List.of(menuItem1, menuItem2);

        // when
        List<MenuItemEntity> found = menuItemJpaRepository.findAllByRestaurantRestaurantId(restaurant.getRestaurantId());

        // then
        assertThat(found)
                .hasSize(2)
                .extracting(MenuItemEntity::getMenuItemId)
                .containsOnly(menuItems.get(0).getMenuItemId(), menuItems.get(1).getMenuItemId());
    }

}
