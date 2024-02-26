package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Customer;
import org.example.domain.Opinion;
import org.example.domain.Orders;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OpinionEntity;
import org.example.infrastructure.database.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {


    Customer mapFromEntity(CustomerEntity customer);
    CustomerEntity mapToEntity(Customer customer);


}
