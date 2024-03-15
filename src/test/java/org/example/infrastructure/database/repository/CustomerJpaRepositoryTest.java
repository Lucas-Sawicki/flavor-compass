package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.domain.Customer;
import org.example.infrastructure.database.entity.AddressEntity;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
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
public class CustomerJpaRepositoryTest {

    private CustomerJpaRepository customerJpaRepository;

    @Test
    @Sql("/test-data.sql")
    void thatCustomerCanBeSavedAndFindCorrectly() {
        //given
        var customers = List.of(someCustomer1(), someCustomer2());

        //when
        List<CustomerEntity> customersFound = customerJpaRepository.findAll();
        //then
        Assertions.assertThat(customersFound.size()).isEqualTo(2);
        Assertions.assertThat(customersFound).containsExactlyInAnyOrderElementsOf(customers);
    }

}
