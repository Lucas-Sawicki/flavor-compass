package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @Sql("/test-data.sql")
    public void shouldFindByEmailReturnUser() {
        // given
        UserEntity user = EntityFixtures.someUser4();
        // when
        Optional<UserEntity> foundUser = userJpaRepository.findByEmail(user.getEmail());

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldFindByEmailReturnEmptyOptional() {
        // given

        // when
        Optional<UserEntity> foundUser = userJpaRepository.findByEmail("nonexisting@example.com");

        // then
        assertThat(foundUser).isEmpty();
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldExistsByEmailReturnTrue() {
        // given
        UserEntity user = EntityFixtures.someUser3();

        // when
        boolean exists = userJpaRepository.existsByEmail(user.getEmail());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldExistsByEmailReturnFalse() {
        // given

        // when
        boolean exists = userJpaRepository.existsByEmail("nonexisting@example.com");

        // then
        assertThat(exists).isFalse();
    }

}
