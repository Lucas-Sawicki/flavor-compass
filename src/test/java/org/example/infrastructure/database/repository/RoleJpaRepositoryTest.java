package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.RoleJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleJpaRepositoryTest {


    private RoleJpaRepository roleJpaRepository;

    @Test
    @Sql("/test-data.sql")
    public void shouldFindRoleForUsersCorrectly() {
        // given
        UserEntity user1 = EntityFixtures.someUser1();
        UserEntity user2 = EntityFixtures.someUser2();
        UserEntity user3 = EntityFixtures.someUser3();
        UserEntity user4 = EntityFixtures.someUser4();

        List<UserEntity> userEntityList = List.of(user1, user2, user3, user4);

        // when
        Optional<RoleEntity> foundRole = roleJpaRepository.findByRole("ROLE_OWNER");


        // then
        assertThat(foundRole).isPresent();
        long countUsersWithRole = userEntityList.stream()
                .filter(user -> user.getRoles().contains(foundRole.get()))
                .count();
        assertThat(countUsersWithRole).isEqualTo(2);
    }
}
