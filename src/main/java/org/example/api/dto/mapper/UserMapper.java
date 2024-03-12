package org.example.api.dto.mapper;

import org.example.api.dto.AddressDTO;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.rest.RestRegistrationDTO;
import org.example.domain.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default Customer mapCustomer(RegistrationDTO registrationDTO, AddressDTO addressDTO) {
        Customer customer = Customer.builder()
                .name(registrationDTO.getName())
                .surname(registrationDTO.getSurname())
                .phone(registrationDTO.getPhone())
                .build();

        User user = User.builder()
                .email(registrationDTO.getEmail())
                .password(registrationDTO.getPassword())
                .active(true)
                .build();

        Address address = Address.builder()
                .country(addressDTO.getAddressCountry())
                .city(addressDTO.getAddressCity())
                .postalCode(addressDTO.getAddressPostalCode())
                .street(addressDTO.getAddressStreet())
                .build();

        return customer.withAddress(address).withUser(user);
    }

    default Owner mapOwner(RegistrationDTO registrationDTO) {
        Owner owner = Owner.builder()
                .name(registrationDTO.getName())
                .surname(registrationDTO.getSurname())
                .phone(registrationDTO.getPhone())
                .build();

        User user = User.builder()
                .email(registrationDTO.getEmail())
                .password(registrationDTO.getPassword())
                .active(true)
                .build();
        return owner.withUser(user);
    }

    default User mapApiUser(RestRegistrationDTO registrationDTO) {
        return User.builder()
                .email(registrationDTO.getEmail())
                .password(registrationDTO.getPassword())
                .active(true)
                .build();
    }
}
