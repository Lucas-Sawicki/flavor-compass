package org.example.api.dto.mapper;

import org.example.api.dto.CustomerDTO;
import org.example.api.dto.CustomerRequestDTO;
import org.example.domain.Address;
import org.example.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    default Customer map(CustomerRequestDTO dto) {
        return Customer.builder()
                .name(dto.getCustomerName())
                .surname(dto.getCustomerSurname())
                .phone(dto.getCustomerPhone())
                .email(dto.getCustomerEmail())
                .password(dto.getPassword())
                .address(Address.builder()
                        .country(dto.getCustomerAddressCountry())
                        .city(dto.getCustomerAddressCity())
                        .postalCode(dto.getCustomerAddressPostalCode())
                        .street(dto.getCustomerAddressStreet())
                        .build())
                .build();
    }

    CustomerDTO map(Customer customer);
}
