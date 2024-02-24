package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.domain.Customer;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.example.infrastructure.database.repository.mapper.CustomerEntityMapper;
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
public class CustomerRepositoryDataJpaTest {


    private CustomerRepository customerRepository;

    private CustomerEntityMapper customerEntityMapper;
    private CustomerJpaRepository customerJpaRepository;

    @Test
    void thatCustomerCanBeSavedCorrectly() {
        //given
        var customer = someCustomer1();
        var customers = List.of(someCustomer1(), someCustomer2(), someCustomer3());
        Customer mapCustomer1 = customerEntityMapper.mapFromEntity(customers.get(0));
        Customer mapCustomer2 = customerEntityMapper.mapFromEntity(customers.get(1));
        Customer mapCustomer3 = customerEntityMapper.mapFromEntity(customers.get(2));
        customerRepository.saveCustomer(mapCustomer1);
        customerRepository.saveCustomer(mapCustomer2);
        customerRepository.saveCustomer(mapCustomer3);
        //when
        List<CustomerEntity> customersFound = customerJpaRepository.findAll();
        //then
        Assertions.assertThat(customersFound.size()).isEqualTo(3);
    }

}
