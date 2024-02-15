package org.example.api.dto.mapper;

import org.example.api.dto.CustomerRequestDTO;
import org.example.api.dto.OwnerRequestDTO;
import org.example.domain.*;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {


    default Customer map(CustomerRequestDTO dto) {
        return Customer.builder()
                .name(dto.getCustomerName())
                .surname(dto.getCustomerSurname())
                .phone(dto.getCustomerPhone())
                .user(User.builder()
                        .email(dto.getCustomerEmail())
                        .password(dto.getCustomerPassword())
                        .active(true)
                        .role(Role.builder()
                                .role("CUSTOMER")
                                .build())
                        .build())
                .address(Address.builder()
                        .country(dto.getCustomerAddressCountry())
                        .city(dto.getCustomerAddressCity())
                        .postalCode(dto.getCustomerAddressPostalCode())
                        .street(dto.getCustomerAddressStreet())
                        .build())
                .build();
    }

    default Owner map(OwnerRequestDTO dto) {
        return Owner.builder()
                .name(dto.getOwnerName())
                .surname(dto.getOwnerSurname())
                .phone(dto.getOwnerPhone())
                .user(User.builder()
                        .email(dto.getOwnerEmail())
                        .active(true)
                        .password(dto.getOwnerPassword())
                        .role(Role.builder()
                                .role("OWNER")
                                .build())
                        .build())
                .build();
    }

}
