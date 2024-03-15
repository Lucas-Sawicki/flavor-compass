package org.example.api.dto.mapper;

import org.example.api.dto.AddressDTO;
import org.example.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    default Address map(AddressDTO addressDTO) {
       return Address.builder()
                .country(addressDTO.getAddressCountry())
                .city(addressDTO.getAddressCity())
                .street(addressDTO.getAddressStreet())
                .postalCode(addressDTO.getAddressPostalCode())
                .build();
    }
    default AddressDTO map(Address address) {
       return AddressDTO.builder()
                .addressCountry(address.getCountry())
                .addressCity(address.getCity())
                .addressStreet(address.getStreet())
                .addressPostalCode(address.getPostalCode())
                .build();
    }
}
