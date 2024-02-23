package org.example.business.dao;

import org.example.domain.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDAO {
    Address saveAddress(Address address);
}
