package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.DeliveryRangeEntity;
import org.example.infrastructure.database.repository.jpa.DeliveryRangeJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryRangeJpaRepositoryTest {



    private TestEntityManager entityManager;

    private DeliveryRangeJpaRepository deliveryRangeJpaRepository;
    @Test
    public void shouldFindByCityAndStreetSuccessfully() {
        // given
        DeliveryRangeEntity deliveryRange = new DeliveryRangeEntity();
        deliveryRange.setCity("Warszawa");
        deliveryRange.setStreet("Krakowskie Przedmieście");
        entityManager.persist(deliveryRange);
        entityManager.flush();

        // when
        Optional<DeliveryRangeEntity> found = deliveryRangeJpaRepository.findByCityAndStreet(deliveryRange.getCity(), deliveryRange.getStreet());

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getCity(), deliveryRange.getCity());
        assertEquals(found.get().getStreet(), deliveryRange.getStreet());
    }

    @Test
    public void shouldFindByCitySuccessfully() {
        // given
        DeliveryRangeEntity deliveryRange1 = new DeliveryRangeEntity();
        deliveryRange1.setCity("Warszawa");
        deliveryRange1.setStreet("Krakowskie Przedmieście");
        entityManager.persist(deliveryRange1);

        DeliveryRangeEntity deliveryRange2 = new DeliveryRangeEntity();
        deliveryRange2.setCity("Warszawa");
        deliveryRange2.setStreet("Nowy Świat");
        entityManager.persist(deliveryRange2);
        entityManager.flush();

        // when
        List<DeliveryRangeEntity> found = deliveryRangeJpaRepository.findByCity("Warszawa");

        // then
        assertEquals(2, found.size());
    }

    @Test
    public void shouldFindByStreetSuccessfully() {
        // given
        DeliveryRangeEntity deliveryRange1 = new DeliveryRangeEntity();
        deliveryRange1.setCity("Warszawa");
        deliveryRange1.setStreet("Krakowskie Przedmieście");
        entityManager.persist(deliveryRange1);

        DeliveryRangeEntity deliveryRange2 = new DeliveryRangeEntity();
        deliveryRange2.setCity("Kraków");
        deliveryRange2.setStreet("Krakowskie Przedmieście");
        entityManager.persist(deliveryRange2);
        entityManager.flush();

        // when
        List<DeliveryRangeEntity> found = deliveryRangeJpaRepository.findByStreet("Krakowskie Przedmieście");

        // then
        assertEquals(2, found.size());
    }

}
