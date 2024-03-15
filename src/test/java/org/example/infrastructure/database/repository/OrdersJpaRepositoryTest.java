package org.example.infrastructure.database.repository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.domain.exception.NotFoundException;
import org.example.infrastructure.database.entity.*;
import org.example.infrastructure.database.repository.jpa.*;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.example.util.EntityFixtures.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersJpaRepositoryTest {

    private OrdersJpaRepository ordersJpaRepository;



    @Test
    @Sql("/test-data.sql")
    public void shouldFindByOrderNumberCorrectly() {
        // given
        OrdersEntity ordersEntity = someOrder1();
        // when
        Optional<OrdersEntity> found = ordersJpaRepository.findByOrderNumber(ordersEntity.getOrderNumber());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getOrderNumber()).isEqualTo(2024030874121L);
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldDeleteByOrderNumber() {
        //given
        OrdersEntity ordersEntity = someOrder2();

        //when
        ordersJpaRepository.deleteByOrderNumber(ordersEntity.getOrderNumber());
        Optional<OrdersEntity> orders = ordersJpaRepository.findByOrderNumber(ordersEntity.getOrderNumber());
        List<OrdersEntity> remainingOrders = ordersJpaRepository.findAll();
        //then
        assertThat(orders).isEmpty();
        assertThat(remainingOrders).isNotEmpty();
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldFindOrdersForSpecificCustomerCorrectly() {
        // given
        CustomerEntity customerEntity = someCustomer1();
        // when
        List<OrdersEntity> found = ordersJpaRepository.findByCustomer(customerEntity);

        // then
        assertThat(found).hasSize(1);
    }

    @Test
    @Sql("/test-data.sql")
    public void shouldFindOrdersForSpecificOwnerCorrectly() {
        // given
        OwnerEntity owner = EntityFixtures.someOwner1();
        OrdersEntity ordersEntity1 = EntityFixtures.someOrder1();
        // when
        List<OrdersEntity> found = ordersJpaRepository.findByOwner(owner);
        // then
        assertThat(found).hasSize(1);
        assertThat(found).extracting(OrdersEntity::getOrderId).containsOnly(ordersEntity1.getOrderId());
    }

}
