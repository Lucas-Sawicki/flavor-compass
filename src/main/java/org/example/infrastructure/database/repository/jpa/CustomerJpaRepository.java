package org.example.infrastructure.database.repository.jpa;

import org.example.domain.Customer;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {

}
