package org.example.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.entity.OrderItemEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.example.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderItemJpaRepositoryTest {


    private OrderItemJpaRepository orderItemJpaRepository;

    @Test
    @Sql("/test-data.sql")
    public void  shouldFindByOrderItemsByOrderIDSuccessfully() {
        // given
        OrdersEntity order = EntityFixtures.someOrder1();

        List<OrderItemEntity> expected = List.of(EntityFixtures.someOrderItem1(), EntityFixtures.someOrderItem2());
        // when
        List<OrderItemEntity> found = orderItemJpaRepository.findByOrdersId(order.getOrderId());

        // then
        assertThat(found).isEqualTo(expected);
        assertThat(found).hasSize(2);
    }
}


