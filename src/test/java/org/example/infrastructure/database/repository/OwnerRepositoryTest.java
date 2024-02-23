package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.domain.Owner;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.example.infrastructure.database.repository.mapper.OwnerEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.example.util.EntityFixtures.*;
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerRepositoryDataJpaTest {

    private OwnerRepository ownerRepository;

    private OwnerEntityMapper ownerEntityMapper;
    private OwnerJpaRepository ownerJpaRepository;

    @Test
    void thatOwnerCanBeSavedCorrectly() {
        //given

        var owner = someOwner1();
        var owners = List.of(someOwner1(), someOwner2(), someOwner3());
        Owner mapOwner1 = ownerEntityMapper.mapFromEntity(owners.get(0));
        Owner mapOwner2 = ownerEntityMapper.mapFromEntity(owners.get(1));
        Owner mapOwner3 = ownerEntityMapper.mapFromEntity(owners.get(2));
        ownerRepository.saveOwner(mapOwner1);
        ownerRepository.saveOwner(mapOwner2);
        ownerRepository.saveOwner(mapOwner3);
        //when
        List<OwnerEntity> ownersFound = ownerJpaRepository.findAll();
        //then

        Assertions.assertThat(ownersFound.size()).isEqualTo(3);
    }
}
