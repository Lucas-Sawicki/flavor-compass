package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.example.util.EntityFixtures.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerJpaRepositoryTest {

    private OwnerJpaRepository ownerJpaRepository;

    @Test
    @Sql("/test-data.sql")
    void thatOwnerCanBeSavedAndFindCorrectly() {
        //given
        var owners = List.of(someOwner1(), someOwner2());
        //when
        List<OwnerEntity> ownersFound = ownerJpaRepository.findAll();
        //then

        Assertions.assertThat(ownersFound.size()).isEqualTo(2);
        Assertions.assertThat(ownersFound).containsExactlyInAnyOrderElementsOf(owners);
    }

    @Test
    @Sql("/test-data.sql")
    void shouldOwnerCanBeFoundByUserCorrectly() {
        //given
        UserEntity user = someUser1();
        //when
        OwnerEntity owner = ownerJpaRepository.findByUser(user);
        //then

        Assertions.assertThat(owner).isNotNull();
        Assertions.assertThat(owner.getUser()).isEqualTo(user);
    }

}
