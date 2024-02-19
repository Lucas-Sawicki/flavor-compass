package org.example.infrastructure.database.repository.mapper;

import org.example.domain.Customer;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "opinions", ignore = true)
    @Mapping(target = "address.restaurant", ignore = true)
    Customer mapFromEntity(CustomerEntity customer);

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "opinions", ignore = true)
    @Mapping(target = "address.restaurant", ignore = true)
    CustomerEntity mapToEntity(Customer customer);
}
